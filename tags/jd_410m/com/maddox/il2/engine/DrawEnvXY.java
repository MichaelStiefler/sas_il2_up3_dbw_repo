package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16;
import com.maddox.util.HashMapXY16Hash;
import com.maddox.util.HashMapXY16List;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public class DrawEnvXY extends DrawEnv
{
  public static final int STEP = 200;
  public static final int MUL_PLATE = 8;
  public static final int STEPPLATE = 1600;
  public static final int MUL_LONG = 8;
  public static final int STEPLONG = 1600;
  private static int[] bitsVisibilityOfLandCells = null;

  private float[] box = new float[4];
  private static Point3d p = new Point3d();
  private float[] uniformCoof = new float[2];
  private HashMapXY16List lstXY;
  private HashMapXY16List lstXYPlate;
  private HashMapXY16Hash mapXY;
  private HashMapXY16Hash mapXYLong;
  private HashMapXY16 distXY;
  private HashMapXY16 distXYPlate;

  public void preRender(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, int paramInt, List paramList1, List paramList2, List paramList3, boolean paramBoolean)
  {
    if (paramBoolean) {
      if (paramList1 != null) paramList1.clear();
      if (paramList2 != null) paramList2.clear();
      if (paramList3 != null) paramList3.clear();
    }
    double d3;
    double d4;
    HashMapExt localHashMapExt1;
    int j;
    HashMapExt localHashMapExt2;
    int m;
    int n;
    int i1;
    HashMapExt localHashMapExt3;
    Object localObject3;
    Object localObject6;
    int i2;
    if ((paramInt & 0x4) != 0) {
      d1 = paramDouble1 - paramFloat;
      d2 = paramDouble2 - paramFloat;
      d3 = paramDouble1 + paramFloat;
      d4 = paramDouble2 + paramFloat;

      localHashMapExt1 = (int)d1 / 200;
      j = (int)d3 / 200;
      localHashMapExt2 = (int)d2 / 200;
      m = (int)d4 / 200;

      for (n = localHashMapExt2; n <= m; n++) {
        for (i1 = localHashMapExt1; i1 <= j; i1++) {
          localHashMapExt3 = this.mapXY.get(n, i1);
          if (localHashMapExt3 != null) {
            localObject3 = localHashMapExt3.nextEntry(null);
            while (localObject3 != null) {
              Engine.cur.profile.drawAll += 1;
              localObject6 = (Actor)((Map.Entry)localObject3).getKey();
              if ((Actor.isValid((Actor)localObject6)) && ((((Actor)localObject6).flags & 0x1) != 0) && (((Actor)localObject6).draw != null)) {
                i2 = ((Actor)localObject6).draw.preRender((Actor)localObject6);
                if (i2 > 0)
                  preRender((Actor)localObject6, i2, paramList1, paramList2, paramList3);
              }
              localObject3 = localHashMapExt3.nextEntry((Map.Entry)localObject3);
            }
          }
        }
      }
    }
    if ((paramInt & 0x8) != 0) {
      d1 = paramDouble1 - paramFloat;
      d2 = paramDouble2 - paramFloat;
      d3 = paramDouble1 + paramFloat;
      d4 = paramDouble2 + paramFloat;

      localHashMapExt1 = (int)d1 / 1600;
      j = (int)d3 / 1600;
      localHashMapExt2 = (int)d2 / 1600;
      m = (int)d4 / 1600;

      for (n = localHashMapExt2; n <= m; n++) {
        for (i1 = localHashMapExt1; i1 <= j; i1++) {
          localHashMapExt3 = this.mapXYLong.get(n, i1);
          if (localHashMapExt3 != null) {
            localObject3 = localHashMapExt3.nextEntry(null);
            while (localObject3 != null) {
              Engine.cur.profile.drawAll += 1;
              localObject6 = (Actor)((Map.Entry)localObject3).getKey();
              if ((Actor.isValid((Actor)localObject6)) && ((((Actor)localObject6).flags & 0x1) != 0) && (((Actor)localObject6).draw != null)) {
                i2 = ((Actor)localObject6).draw.preRender((Actor)localObject6);
                if (i2 > 0)
                  preRender((Actor)localObject6, i2, paramList1, paramList2, paramList3);
              }
              localObject3 = localHashMapExt3.nextEntry((Map.Entry)localObject3);
            }
          }
        }
      }
    }

    double d1 = 0.0D;
    double d2 = 1.0D;
    if (((paramInt & 0x2) != 0) || ((paramInt & 0x1) != 0)) {
      Camera.GetUniformDistParams(this.uniformCoof);
      if (this.uniformCoof[0] > 0.0F) {
        d2 = 1.0F / this.uniformCoof[0];
        d1 = this.uniformCoof[1];
      }
    }
    int i;
    double d19;
    Object localObject2;
    if ((paramInt & 0x2) != 0)
    {
      int i3;
      double d17;
      int i9;
      if ((Engine.land() != null) && (!Engine.land().config.bBig))
      {
        i = Landscape.Compute2DBoundBoxOfVisibleLand(paramFloat, -50, 50, this.box);

        if (i == 0) {
          return;
        }

        this.box[0] -= 80.0F;
        this.box[1] -= 80.0F;
        this.box[2] += 80.0F;
        this.box[3] += 80.0F;

        localHashMapExt1 = (int)this.box[0] / 200;
        j = (int)this.box[1] / 200;
        localHashMapExt2 = (int)this.box[2] / 200;
        m = (int)this.box[3] / 200;

        n = (m - j + 1) * (localHashMapExt2 - localHashMapExt1 + 1);
        if (n <= 0) {
          return;
        }
        i1 = n + 31 >> 5;
        if ((bitsVisibilityOfLandCells == null) || (bitsVisibilityOfLandCells.length < i1))
        {
          bitsVisibilityOfLandCells = new int[i1];
        }

        i = Landscape.ComputeVisibilityOfLandCells(paramFloat, localHashMapExt1, j, localHashMapExt2 - localHashMapExt1 + 1, m - j + 1, 200, 80, -50, 50, bitsVisibilityOfLandCells);

        if (i != 0) {
          n = 0;
          for (i1 = j; i1 <= m; i1++) {
            for (localHashMapExt3 = localHashMapExt1; localHashMapExt3 <= localHashMapExt2; n++) {
              if ((bitsVisibilityOfLandCells[(n >> 5)] & 1 << (n & 0x1F)) != 0)
              {
                localObject3 = this.lstXY.get(i1, localHashMapExt3);
                if (localObject3 != null)
                {
                  localObject6 = (MaxDist)this.distXY.get(i1, localHashMapExt3);

                  if (localObject6 != null) { double d9 = (localHashMapExt3 + 0.5D) * 200.0D;
                    double d11 = (i1 + 0.5D) * 200.0D;
                    double d15 = (paramDouble1 - d9) * (paramDouble1 - d9) + (paramDouble2 - d11) * (paramDouble2 - d11) + (paramDouble3 - ((MaxDist)localObject6).z) * (paramDouble3 - ((MaxDist)localObject6).z);

                    d19 = (((MaxDist)localObject6).dist - d1) * d2 + 280.0D;

                    if (d15 > d19 * d19);
                  } else {
                    i3 = ((List)localObject3).size();
                    for (int i5 = 0; i5 < i3; i5++) {
                      Actor localActor1 = (Actor)((List)localObject3).get(i5);
                      if ((!Actor.isValid(localActor1)) || ((localActor1.flags & 0x1) == 0) || (localActor1.draw == null))
                        continue;
                      if (localActor1.draw.uniformMaxDist > 0.0F) {
                        localActor1.pos.getRender(p);
                        double d13 = (paramDouble1 - p.x) * (paramDouble1 - p.x) + (paramDouble2 - p.y) * (paramDouble2 - p.y) + (paramDouble3 - p.z) * (paramDouble3 - p.z);

                        d17 = (localActor1.draw.uniformMaxDist - d1) * d2;
                        if (d13 > d17 * d17)
                        {
                          continue;
                        }
                      }
                      Engine.cur.profile.drawAll += 1;
                      i9 = localActor1.draw.preRender(localActor1);
                      if (i9 > 0)
                        preRender(localActor1, i9, paramList1, paramList2, paramList3);
                    }
                  }
                }
              }
              localHashMapExt3++;
            }

          }

        }

      }
      else
      {
        d3 = paramDouble1 - paramFloat;
        d4 = paramDouble2 - paramFloat;
        double d5 = paramDouble1 + paramFloat;
        double d7 = paramDouble2 + paramFloat;

        n = (int)d3 / 200;
        i1 = (int)d5 / 200;
        localObject2 = (int)d4 / 200;
        Object localObject4 = (int)d7 / 200;

        for (localObject6 = localObject2; localObject6 <= localObject4; localObject6++) {
          for (i3 = n; i3 <= i1; i3++) {
            List localList2 = this.lstXY.get(localObject6, i3);
            if (localList2 == null)
              continue;
            int i7 = localList2.size();
            for (i9 = 0; i9 < i7; i9++) {
              Actor localActor3 = (Actor)localList2.get(i9);
              if ((!Actor.isValid(localActor3)) || ((localActor3.flags & 0x1) == 0) || (localActor3.draw == null))
                continue;
              if (localActor3.draw.uniformMaxDist > 0.0F) {
                localActor3.pos.getRender(p);
                d17 = (paramDouble1 - p.x) * (paramDouble1 - p.x) + (paramDouble2 - p.y) * (paramDouble2 - p.y) + (paramDouble3 - p.z) * (paramDouble3 - p.z);

                double d20 = (localActor3.draw.uniformMaxDist - d1) * d2;
                if (d17 > d20 * d20)
                {
                  continue;
                }
              }
              Engine.cur.profile.drawAll += 1;
              int i11 = localActor3.draw.preRender(localActor3);
              if (i11 > 0) {
                preRender(localActor3, i11, paramList1, paramList2, paramList3);
              }
            }
          }
        }
      }

    }

    if ((paramInt & 0x1) != 0)
    {
      int i4;
      int i10;
      if ((Engine.land() != null) && (!Engine.land().config.bBig))
      {
        i = Landscape.Compute2DBoundBoxOfVisibleLand(paramFloat, -1, 1, this.box);

        if (i == 0) {
          return;
        }

        this.box[0] -= 150.0F;
        this.box[1] -= 150.0F;
        this.box[2] += 150.0F;
        this.box[3] += 150.0F;

        Object localObject1 = (int)this.box[0] / 1600;
        j = (int)this.box[1] / 1600;
        int k = (int)this.box[2] / 1600;
        m = (int)this.box[3] / 1600;

        n = (m - j + 1) * (k - localObject1 + 1);
        if (n <= 0) {
          return;
        }
        i1 = n + 31 >> 5;
        if ((bitsVisibilityOfLandCells == null) || (bitsVisibilityOfLandCells.length < i1))
        {
          bitsVisibilityOfLandCells = new int[i1];
        }

        i = Landscape.ComputeVisibilityOfLandCells(paramFloat, localObject1, j, k - localObject1 + 1, m - j + 1, 1600, 150, -1, 1, bitsVisibilityOfLandCells);

        if (i != 0) {
          n = 0;
          for (i1 = j; i1 <= m; i1++) {
            for (localObject2 = localObject1; localObject2 <= k; n++) {
              if ((bitsVisibilityOfLandCells[(n >> 5)] & 1 << (n & 0x1F)) != 0)
              {
                List localList1 = this.lstXYPlate.get(i1, localObject2);
                if (localList1 != null)
                {
                  localObject6 = (MaxDist)this.distXYPlate.get(i1, localObject2);

                  if (localObject6 != null) { double d10 = (localObject2 + 0.5D) * 200.0D * 8.0D;
                    double d12 = (i1 + 0.5D) * 200.0D * 8.0D;
                    double d16 = (paramDouble1 - d10) * (paramDouble1 - d10) + (paramDouble2 - d12) * (paramDouble2 - d12) + (paramDouble3 - ((MaxDist)localObject6).z) * (paramDouble3 - ((MaxDist)localObject6).z);

                    d19 = (((MaxDist)localObject6).dist - d1) * d2 + 2240.0D;
                    if (d16 > d19 * d19);
                  } else {
                    i4 = localList1.size();
                    for (int i6 = 0; i6 < i4; i6++) {
                      Actor localActor2 = (Actor)localList1.get(i6);
                      if ((Actor.isValid(localActor2)) && ((localActor2.flags & 0x1) != 0) && (localActor2.draw != null)) {
                        if (localActor2.draw.uniformMaxDist > 0.0F) {
                          localActor2.pos.getRender(p);
                          double d14 = (paramDouble1 - p.x) * (paramDouble1 - p.x) + (paramDouble2 - p.y) * (paramDouble2 - p.y) + (paramDouble3 - p.z) * (paramDouble3 - p.z);

                          double d18 = (localActor2.draw.uniformMaxDist - d1) * d2;
                          if (d14 > d18 * d18)
                          {
                            continue;
                          }
                        }

                        Engine.cur.profile.drawAll += 1;
                        i10 = localActor2.draw.preRender(localActor2);
                        if (i10 > 0) {
                          if ((i10 & 0x2) != 0) paramList2.add(localActor2);
                          else if ((i10 & 0x1) != 0) paramList1.add(localActor2);
                          Engine.cur.profile.draw += 1;
                        }
                      }
                    }
                  }
                }
              }
              localObject2++;
            }

          }

        }

      }
      else
      {
        d3 = paramDouble1 - paramFloat;
        d4 = paramDouble2 - paramFloat;
        double d6 = paramDouble1 + paramFloat;
        double d8 = paramDouble2 + paramFloat;

        n = (int)d3 / 1600;
        i1 = (int)d6 / 1600;
        localObject2 = (int)d4 / 1600;
        Object localObject5 = (int)d8 / 1600;

        for (localObject6 = localObject2; localObject6 <= localObject5; localObject6++)
          for (i4 = n; i4 <= i1; i4++) {
            List localList3 = this.lstXYPlate.get(localObject6, i4);
            if (localList3 == null)
              continue;
            int i8 = localList3.size();
            for (i10 = 0; i10 < i8; i10++) {
              Engine.cur.profile.drawAll += 1;
              Actor localActor4 = (Actor)localList3.get(i10);
              if ((Actor.isValid(localActor4)) && ((localActor4.flags & 0x1) != 0) && (localActor4.draw != null)) {
                int i12 = localActor4.draw.preRender(localActor4);
                if (i12 > 0) {
                  if ((i12 & 0x2) != 0) paramList2.add(localActor4);
                  else if ((i12 & 0x1) != 0) paramList1.add(localActor4);
                  Engine.cur.profile.draw += 1;
                }
              }
            }
          }
      }
    }
  }

  public void getFiltered(AbstractCollection paramAbstractCollection, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt, ActorFilter paramActorFilter)
  {
    double d1;
    if (paramDouble3 < paramDouble1) { d1 = paramDouble1; paramDouble1 = paramDouble3; paramDouble3 = d1; }
    if (paramDouble4 < paramDouble2) { d1 = paramDouble2; paramDouble2 = paramDouble4; paramDouble4 = d1; }
    double d2 = (paramDouble3 + paramDouble1) / 2.0D;
    double d3 = (paramDouble4 + paramDouble2) / 2.0D;
    int i = (int)paramDouble1 / 200;
    int j = (int)paramDouble3 / 200;
    int k = (int)paramDouble2 / 200;
    int m = (int)paramDouble4 / 200;
    int n;
    int i1;
    Object localObject1;
    Object localObject2;
    if ((paramInt & 0x4) != 0)
      for (n = k; n <= m; n++)
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.mapXY.get(n, i1);
          if (localObject1 != null) {
            Map.Entry localEntry1 = ((HashMapExt)localObject1).nextEntry(null);
            while (localEntry1 != null) {
              Actor localActor1 = (Actor)localEntry1.getKey();
              if (Actor.isValid(localActor1)) {
                localObject2 = localActor1.pos.getAbsPoint();
                double d4 = (((Point3d)localObject2).x - d2) * (((Point3d)localObject2).x - d2) + (((Point3d)localObject2).y - d3) * (((Point3d)localObject2).y - d3);
                if ((paramActorFilter.isUse(localActor1, d4)) && 
                  (paramAbstractCollection != null)) paramAbstractCollection.add(localActor1);
              }
              localEntry1 = ((HashMapExt)localObject1).nextEntry(localEntry1);
            }
          }
        }
    double d6;
    if ((paramInt & 0x2) != 0) {
      for (n = k; n <= m; n++) {
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.lstXY.get(n, i1);
          if (localObject1 != null) {
            int i2 = ((List)localObject1).size();
            for (int i4 = 0; i4 < i2; i4++) {
              localObject2 = (Actor)((List)localObject1).get(i4);
              if (Actor.isValid((Actor)localObject2)) {
                Point3d localPoint3d1 = ((Actor)localObject2).pos.getAbsPoint();
                d6 = (localPoint3d1.x - d2) * (localPoint3d1.x - d2) + (localPoint3d1.y - d3) * (localPoint3d1.y - d3);
                if ((paramActorFilter.isUse((Actor)localObject2, d6)) && 
                  (paramAbstractCollection != null)) paramAbstractCollection.add(localObject2);
              }
            }
          }
        }
      }
    }
    if ((paramInt & 0x8) != 0) {
      i = (int)paramDouble1 / 1600;
      j = (int)paramDouble3 / 1600;
      k = (int)paramDouble2 / 1600;
      m = (int)paramDouble4 / 1600;
      for (n = k; n <= m; n++) {
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.mapXYLong.get(n, i1);
          if (localObject1 != null) {
            Map.Entry localEntry2 = ((HashMapExt)localObject1).nextEntry(null);
            while (localEntry2 != null) {
              Actor localActor2 = (Actor)localEntry2.getKey();
              if (Actor.isValid(localActor2)) {
                localObject2 = localActor2.pos.getAbsPoint();
                double d5 = (((Point3d)localObject2).x - d2) * (((Point3d)localObject2).x - d2) + (((Point3d)localObject2).y - d3) * (((Point3d)localObject2).y - d3);
                if ((paramActorFilter.isUse(localActor2, d5)) && 
                  (paramAbstractCollection != null)) paramAbstractCollection.add(localActor2);
              }
              localEntry2 = ((HashMapExt)localObject1).nextEntry(localEntry2);
            }
          }
        }
      }
    }
    if ((paramInt & 0x1) != 0) {
      i = (int)paramDouble1 / 1600;
      j = (int)paramDouble3 / 1600;
      k = (int)paramDouble2 / 1600;
      m = (int)paramDouble4 / 1600;
      for (n = k; n <= m; n++)
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.lstXYPlate.get(n, i1);
          if (localObject1 != null) {
            int i3 = ((List)localObject1).size();
            for (int i5 = 0; i5 < i3; i5++) {
              localObject2 = (Actor)((List)localObject1).get(i5);
              if (Actor.isValid((Actor)localObject2)) {
                Point3d localPoint3d2 = ((Actor)localObject2).pos.getAbsPoint();
                d6 = (localPoint3d2.x - d2) * (localPoint3d2.x - d2) + (localPoint3d2.y - d3) * (localPoint3d2.y - d3);
                if ((paramActorFilter.isUse((Actor)localObject2, d6)) && 
                  (paramAbstractCollection != null)) paramAbstractCollection.add(localObject2);
              }
            }
          }
        }
    }
  }

  public void getFiltered(AbstractMap paramAbstractMap, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt, ActorFilter paramActorFilter)
  {
    double d1;
    if (paramDouble3 < paramDouble1) { d1 = paramDouble1; paramDouble1 = paramDouble3; paramDouble3 = d1; }
    if (paramDouble4 < paramDouble2) { d1 = paramDouble2; paramDouble2 = paramDouble4; paramDouble4 = d1; }
    double d2 = (paramDouble3 + paramDouble1) / 2.0D;
    double d3 = (paramDouble4 + paramDouble2) / 2.0D;
    int i = (int)paramDouble1 / 200;
    int j = (int)paramDouble3 / 200;
    int k = (int)paramDouble2 / 200;
    int m = (int)paramDouble4 / 200;
    int n;
    int i1;
    Object localObject1;
    Object localObject2;
    if ((paramInt & 0x4) != 0)
      for (n = k; n <= m; n++)
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.mapXY.get(n, i1);
          if (localObject1 != null) {
            Map.Entry localEntry1 = ((HashMapExt)localObject1).nextEntry(null);
            while (localEntry1 != null) {
              Actor localActor1 = (Actor)localEntry1.getKey();
              if (Actor.isValid(localActor1)) {
                localObject2 = localActor1.pos.getAbsPoint();
                double d4 = (((Point3d)localObject2).x - d2) * (((Point3d)localObject2).x - d2) + (((Point3d)localObject2).y - d3) * (((Point3d)localObject2).y - d3);
                if ((paramActorFilter.isUse(localActor1, d4)) && 
                  (paramAbstractMap != null)) paramAbstractMap.put(localActor1, null);
              }
              localEntry1 = ((HashMapExt)localObject1).nextEntry(localEntry1);
            }
          }
        }
    double d6;
    if ((paramInt & 0x2) != 0) {
      for (n = k; n <= m; n++) {
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.lstXY.get(n, i1);
          if (localObject1 != null) {
            int i2 = ((List)localObject1).size();
            for (int i4 = 0; i4 < i2; i4++) {
              localObject2 = (Actor)((List)localObject1).get(i4);
              if (Actor.isValid((Actor)localObject2)) {
                Point3d localPoint3d1 = ((Actor)localObject2).pos.getAbsPoint();
                d6 = (localPoint3d1.x - d2) * (localPoint3d1.x - d2) + (localPoint3d1.y - d3) * (localPoint3d1.y - d3);
                if ((paramActorFilter.isUse((Actor)localObject2, d6)) && 
                  (paramAbstractMap != null)) paramAbstractMap.put(localObject2, null);
              }
            }
          }
        }
      }
    }
    if ((paramInt & 0x8) != 0) {
      i = (int)paramDouble1 / 1600;
      j = (int)paramDouble3 / 1600;
      k = (int)paramDouble2 / 1600;
      m = (int)paramDouble4 / 1600;
      for (n = k; n <= m; n++) {
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.mapXYLong.get(n, i1);
          if (localObject1 != null) {
            Map.Entry localEntry2 = ((HashMapExt)localObject1).nextEntry(null);
            while (localEntry2 != null) {
              Actor localActor2 = (Actor)localEntry2.getKey();
              if (Actor.isValid(localActor2)) {
                localObject2 = localActor2.pos.getAbsPoint();
                double d5 = (((Point3d)localObject2).x - d2) * (((Point3d)localObject2).x - d2) + (((Point3d)localObject2).y - d3) * (((Point3d)localObject2).y - d3);
                if ((paramActorFilter.isUse(localActor2, d5)) && 
                  (paramAbstractMap != null)) paramAbstractMap.put(localActor2, null);
              }
              localEntry2 = ((HashMapExt)localObject1).nextEntry(localEntry2);
            }
          }
        }
      }
    }
    if ((paramInt & 0x1) != 0) {
      i = (int)paramDouble1 / 1600;
      j = (int)paramDouble3 / 1600;
      k = (int)paramDouble2 / 1600;
      m = (int)paramDouble4 / 1600;
      for (n = k; n <= m; n++)
        for (i1 = i; i1 <= j; i1++) {
          localObject1 = this.lstXYPlate.get(n, i1);
          if (localObject1 != null) {
            int i3 = ((List)localObject1).size();
            for (int i5 = 0; i5 < i3; i5++) {
              localObject2 = (Actor)((List)localObject1).get(i5);
              if (Actor.isValid((Actor)localObject2)) {
                Point3d localPoint3d2 = ((Actor)localObject2).pos.getAbsPoint();
                d6 = (localPoint3d2.x - d2) * (localPoint3d2.x - d2) + (localPoint3d2.y - d3) * (localPoint3d2.y - d3);
                if ((paramActorFilter.isUse((Actor)localObject2, d6)) && 
                  (paramAbstractMap != null)) paramAbstractMap.put(localObject2, null);
              }
            }
          }
        }
    }
  }

  public void staticTrimToSize()
  {
    this.lstXY.allValuesTrimToSize();
    this.lstXYPlate.allValuesTrimToSize();
  }

  public void setUniformMaxDist(int paramInt1, int paramInt2, float paramFloat)
  {
    MaxDist localMaxDist = new MaxDist();
    localMaxDist.dist = paramFloat;
    float f = Landscape.H((paramInt1 + 0.5F) * 200.0F, (paramInt2 + 0.5F) * 200.0F);
    localMaxDist.z = f;
    this.distXY.put(paramInt2, paramInt1, localMaxDist);
    paramInt1 /= 8;
    paramInt2 /= 8;
    localMaxDist = (MaxDist)this.distXYPlate.get(paramInt2, paramInt1);
    if (localMaxDist != null) {
      if (localMaxDist.dist < paramFloat)
        localMaxDist.dist = paramFloat;
    } else {
      localMaxDist = new MaxDist();
      localMaxDist.dist = paramFloat;
      localMaxDist.z = f;
      this.distXYPlate.put(paramInt2, paramInt1, localMaxDist);
    }
  }

  protected void changedPos(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    int i;
    int j;
    int k;
    int m;
    if (((paramActor instanceof VisibilityLong)) && (((VisibilityLong)paramActor).isVisibilityLong())) {
      i = (int)paramPoint3d1.x / 1600;
      j = (int)paramPoint3d1.y / 1600;
      k = (int)paramPoint3d2.x / 1600;
      m = (int)paramPoint3d2.y / 1600;
      if ((i != k) || (j != m)) {
        this.mapXYLong.remove(j, i, paramActor);
        this.mapXYLong.put(m, k, paramActor, null);
      }
    } else {
      i = (int)paramPoint3d1.x / 200;
      j = (int)paramPoint3d1.y / 200;
      k = (int)paramPoint3d2.x / 200;
      m = (int)paramPoint3d2.y / 200;
      if ((i != k) || (j != m)) {
        this.mapXY.remove(j, i, paramActor);
        this.mapXY.put(m, k, paramActor, null);
      }
    }
  }

  protected void add(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      int i;
      int j;
      if (((paramActor instanceof VisibilityLong)) && (((VisibilityLong)paramActor).isVisibilityLong())) {
        i = (int)localPoint3d.x / 1600;
        j = (int)localPoint3d.y / 1600;
        this.mapXYLong.put(j, i, paramActor, null);
      } else {
        i = (int)localPoint3d.x / 200;
        j = (int)localPoint3d.y / 200;
        this.mapXY.put(j, i, paramActor, null);
      }
    }
  }

  protected void remove(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      int i;
      int j;
      if (((paramActor instanceof VisibilityLong)) && (((VisibilityLong)paramActor).isVisibilityLong())) {
        i = (int)localPoint3d.x / 1600;
        j = (int)localPoint3d.y / 1600;
        this.mapXYLong.remove(j, i, paramActor);
      } else {
        i = (int)localPoint3d.x / 200;
        j = (int)localPoint3d.y / 200;
        this.mapXY.remove(j, i, paramActor);
      }
    }
  }

  protected void changedPosStatic(Actor paramActor, Point3d paramPoint3d1, Point3d paramPoint3d2)
  {
    int i;
    int j;
    int k;
    int m;
    if ((paramActor instanceof LandPlate)) {
      i = (int)paramPoint3d1.x / 1600;
      j = (int)paramPoint3d1.y / 1600;
      k = (int)paramPoint3d2.x / 1600;
      m = (int)paramPoint3d2.y / 1600;
      if ((i != k) || (j != m)) {
        this.lstXYPlate.remove(j, i, paramActor);
        this.lstXYPlate.put(m, k, paramActor);
      }
    } else {
      i = (int)paramPoint3d1.x / 200;
      j = (int)paramPoint3d1.y / 200;
      k = (int)paramPoint3d2.x / 200;
      m = (int)paramPoint3d2.y / 200;
      if ((i != k) || (j != m)) {
        this.lstXY.remove(j, i, paramActor);
        this.lstXY.put(m, k, paramActor);
      }
    }
  }

  protected void addStatic(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      int i;
      int j;
      if ((paramActor instanceof LandPlate)) {
        i = (int)localPoint3d.x / 1600;
        j = (int)localPoint3d.y / 1600;
        this.lstXYPlate.put(j, i, paramActor);
      } else {
        i = (int)localPoint3d.x / 200;
        j = (int)localPoint3d.y / 200;
        this.lstXY.put(j, i, paramActor);
      }
    }
  }

  protected void removeStatic(Actor paramActor)
  {
    if (paramActor.pos != null) {
      Point3d localPoint3d = paramActor.pos.getCurrentPoint();
      int i;
      int j;
      if ((paramActor instanceof LandPlate)) {
        i = (int)localPoint3d.x / 1600;
        j = (int)localPoint3d.y / 1600;
        this.lstXYPlate.remove(j, i, paramActor);
      } else {
        i = (int)localPoint3d.x / 200;
        j = (int)localPoint3d.y / 200;
        this.lstXY.remove(j, i, paramActor);
      }
    }
  }

  protected void clear() {
    this.mapXY.clear();
    this.mapXYLong.clear();
    this.lstXY.clear();
    this.lstXYPlate.clear();
    this.distXY.clear();
    this.distXYPlate.clear();
  }

  public void resetGameClear() {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    this.mapXY.allValues(localArrayList1);
    Object localObject;
    for (int i = 0; i < localArrayList1.size(); i++) {
      localObject = (HashMapExt)localArrayList1.get(i);
      localArrayList2.addAll(((HashMapExt)localObject).keySet());
      Engine.destroyListGameActors(localArrayList2);
    }
    localArrayList1.clear();

    this.mapXYLong.allValues(localArrayList1);
    for (i = 0; i < localArrayList1.size(); i++) {
      localObject = (HashMapExt)localArrayList1.get(i);
      localArrayList2.addAll(((HashMapExt)localObject).keySet());
      Engine.destroyListGameActors(localArrayList2);
    }
    localArrayList1.clear();

    this.lstXY.allValues(localArrayList1);
    this.lstXYPlate.allValues(localArrayList1);
    for (i = 0; i < localArrayList1.size(); i++) {
      localObject = (ArrayList)localArrayList1.get(i);
      localArrayList2.addAll((Collection)localObject);
      Engine.destroyListGameActors(localArrayList2);
    }
    localArrayList1.clear();
    this.distXY.clear();
    this.distXYPlate.clear();
  }
  public void resetGameCreate() {
    clear();
  }

  protected DrawEnvXY() {
    this.mapXY = new HashMapXY16Hash(7);
    this.mapXYLong = new HashMapXY16Hash(7);
    this.lstXY = new HashMapXY16List(7);
    this.lstXYPlate = new HashMapXY16List(7);
    this.distXY = new HashMapXY16();
    this.distXYPlate = new HashMapXY16();
  }

  static class MaxDist
  {
    public float z;
    public double dist;
  }
}