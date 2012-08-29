// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Landscape.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.World;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16;
import java.util.Map;

// Referenced classes of package com.maddox.il2.engine:
//            LandConf, ActorLandMesh, Actor, Mat, 
//            Sun, Config, ActorPos, Mesh

public class Landscape
{
    static class MeshCell
    {

        com.maddox.il2.engine.ActorLandMesh m;
        float hMin;
        float hMax;

        MeshCell(com.maddox.il2.engine.ActorLandMesh actorlandmesh, float f, float f1)
        {
            m = actorlandmesh;
            hMin = f;
            hMax = f1;
        }
    }


    public Landscape()
    {
        config = new LandConf();
        MapName = "";
        sunRise = new Vector3f();
    }

    public final int WORLD2PIXX(double d)
    {
        return (int)(d * 0.004999999888241291D);
    }

    public final int WORLD2PIXX(float f)
    {
        return (int)(f * 0.005F);
    }

    public final int WORLD2PIXY(double d)
    {
        return (int)((double)((float)com.maddox.il2.engine.Landscape.getSizeYpix() - 1.0F) - d * 0.004999999888241291D);
    }

    public final int WORLD2PIXY(float f)
    {
        return (int)((float)com.maddox.il2.engine.Landscape.getSizeYpix() - 1.0F - f * 0.005F);
    }

    private final float WORLD2PIXXf(float f)
    {
        return f * 0.005F;
    }

    private final float WORLD2PIXYf(float f)
    {
        return (float)com.maddox.il2.engine.Landscape.getSizeYpix() - 1.0F - f * 0.005F;
    }

    public final float PIX2WORLDX(float f)
    {
        return f * 200F;
    }

    public final float PIX2WORLDY(float f)
    {
        return ((float)(com.maddox.il2.engine.Landscape.getSizeYpix() - 1) - f) * 200F;
    }

    public static native int getPixelMapT(int i, int j);

    public static native void setPixelMapT(int i, int j, int k);

    public static native int getPixelMapH(int i, int j);

    public static native void setPixelMapH(int i, int j, int k);

    public final boolean isWater(double d, double d1)
    {
        if(bNoWater)
            return false;
        else
            return com.maddox.il2.engine.Landscape.isWater((float)d, (float)d1);
    }

    private static final native boolean isWater(float f, float f1);

    public static final native int estimateNoWater(int i, int j, int k);

    public static final native int getSizeXpix();

    public static final native int getSizeYpix();

    public float getSizeX()
    {
        return 200F * (float)com.maddox.il2.engine.Landscape.getSizeXpix();
    }

    public float getSizeY()
    {
        return 200F * (float)com.maddox.il2.engine.Landscape.getSizeYpix();
    }

    public final double Hmax(double d, double d1)
    {
        return (double)com.maddox.il2.engine.Landscape.Hmax((float)d, (float)d1);
    }

    public final double Hmin(double d, double d1)
    {
        return (double)com.maddox.il2.engine.Landscape.Hmin((float)d, (float)d1);
    }

    public static final float H(float f, float f1)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f2 = com.maddox.il2.engine.Landscape.cH(f, f1);
                float f3 = meshcell.m.cHQ(f, f1);
                return f3 <= f2 ? f2 : f3;
            }
        }
        return com.maddox.il2.engine.Landscape.cH(f, f1);
    }

    public static final float Hmax(float f, float f1)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
                return meshcell.hMax;
        }
        return com.maddox.il2.engine.Landscape.cHmax(f, f1);
    }

    public static final float Hmin(float f, float f1)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
                return meshcell.hMin;
        }
        return com.maddox.il2.engine.Landscape.cHmin(f, f1);
    }

    private static final native float cH(float f, float f1);

    private static final native float cHmax(float f, float f1);

    private static final native float cHmin(float f, float f1);

    public final double HQ_Air(double d, double d1)
    {
        return (double)com.maddox.il2.engine.Landscape.HQ_Air((float)d, (float)d1);
    }

    public static final float HQ_Air(float f, float f1)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f2 = com.maddox.il2.engine.Landscape.cHQ_Air(f, f1);
                float f3 = meshcell.m.cHQ(f, f1);
                return f3 <= f2 ? f2 : f3;
            }
        }
        return com.maddox.il2.engine.Landscape.cHQ_Air(f, f1);
    }

    private static final native float cHQ_Air(float f, float f1);

    public final double HQ_ForestHeightHere(double d, double d1)
    {
        return (double)com.maddox.il2.engine.Landscape.HQ_forestHeightHere((float)d, (float)d1);
    }

    public static final float HQ_forestHeightHere(float f, float f1)
    {
        return com.maddox.il2.engine.Landscape.cHQ_forestHeightHere(f, f1);
    }

    private static final native float cHQ_forestHeightHere(float f, float f1);

    public final int HQ_RoadTypeHere(double d, double d1)
    {
        return com.maddox.il2.engine.Landscape.HQRoadTypeHere((float)d, (float)d1);
    }

    public static final native int HQRoadTypeHere(float f, float f1);

    public final double HQ(double d, double d1)
    {
        return (double)com.maddox.il2.engine.Landscape.HQ((float)d, (float)d1);
    }

    public static final float HQ(float f, float f1)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f2 = com.maddox.il2.engine.Landscape.cHQ(f, f1);
                float f3 = meshcell.m.cHQ(f, f1);
                return f3 <= f2 ? f2 : f3;
            }
        }
        return com.maddox.il2.engine.Landscape.cHQ(f, f1);
    }

    private static final native float cHQ(float f, float f1);

    public final void N(double d, double d1, com.maddox.JGP.Vector3f vector3f)
    {
        if(meshMapXY != null)
        {
            int i = (int)(d * 0.004999999888241291D);
            int j = (int)(d1 * 0.004999999888241291D);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f = com.maddox.il2.engine.Landscape.cHQ((float)d, (float)d1);
                float f1 = meshcell.m.cHQ(d, d1);
                if(f1 >= f && meshcell.m.cNormal(d, d1, EQNBuf))
                {
                    vector3f.set(EQNBuf);
                    return;
                }
            }
        }
        EQNBuf[0] = (float)d;
        EQNBuf[1] = (float)d1;
        EQNBuf[2] = -1F;
        com.maddox.il2.engine.Landscape.cEQN(EQNBuf);
        vector3f.set(EQNBuf);
    }

    public final void N(double d, double d1, com.maddox.JGP.Vector3d vector3d)
    {
        if(meshMapXY != null)
        {
            int i = (int)(d * 0.004999999888241291D);
            int j = (int)(d1 * 0.004999999888241291D);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f = com.maddox.il2.engine.Landscape.cHQ((float)d, (float)d1);
                float f1 = meshcell.m.cHQ(d, d1);
                if(f1 >= f && meshcell.m.cNormal(d, d1, EQNBuf))
                {
                    vector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
                    return;
                }
            }
        }
        EQNBuf[0] = (float)d;
        EQNBuf[1] = (float)d1;
        EQNBuf[2] = -1F;
        com.maddox.il2.engine.Landscape.cEQN(EQNBuf);
        vector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
    }

    public final void N(float f, float f1, com.maddox.JGP.Vector3f vector3f)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f2 = com.maddox.il2.engine.Landscape.cHQ(f, f1);
                float f3 = meshcell.m.cHQ(f, f1);
                if(f3 >= f2 && meshcell.m.cNormal(f, f1, EQNBuf))
                {
                    vector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
                    return;
                }
            }
        }
        EQNBuf[0] = f;
        EQNBuf[1] = f1;
        EQNBuf[2] = -1F;
        com.maddox.il2.engine.Landscape.cEQN(EQNBuf);
        vector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
    }

    public final double EQN(double d, double d1, com.maddox.JGP.Vector3d vector3d)
    {
        if(meshMapXY != null)
        {
            int i = (int)(d * 0.004999999888241291D);
            int j = (int)(d1 * 0.004999999888241291D);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f = com.maddox.il2.engine.Landscape.cHQ((float)d, (float)d1);
                float f1 = meshcell.m.cHQ(d, d1);
                if(f1 >= f && meshcell.m.cPlane(d, d1, EQNBufD))
                {
                    vector3d.set(EQNBufD[0], EQNBufD[1], EQNBufD[2]);
                    return EQNBufD[3];
                }
            }
        }
        EQNBuf[0] = (float)d;
        EQNBuf[1] = (float)d1;
        EQNBuf[2] = 1.0F;
        com.maddox.il2.engine.Landscape.cEQN(EQNBuf);
        vector3d.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
        return (double)EQNBuf[3];
    }

    public final float EQN(float f, float f1, com.maddox.JGP.Vector3f vector3f)
    {
        if(meshMapXY != null)
        {
            int i = (int)(f * 0.005F);
            int j = (int)(f1 * 0.005F);
            com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
            if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            {
                float f2 = com.maddox.il2.engine.Landscape.cHQ(f, f1);
                float f3 = meshcell.m.cHQ(f, f1);
                if(f3 >= f2 && meshcell.m.cPlane(f, f1, EQNBufD))
                {
                    vector3f.set((float)EQNBufD[0], (float)EQNBufD[1], (float)EQNBufD[2]);
                    return (float)EQNBufD[3];
                }
            }
        }
        EQNBuf[0] = f;
        EQNBuf[1] = f1;
        EQNBuf[2] = 1.0F;
        com.maddox.il2.engine.Landscape.cEQN(EQNBuf);
        vector3f.set(EQNBuf[0], EQNBuf[1], EQNBuf[2]);
        return EQNBuf[3];
    }

    public static boolean rayHitHQ(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2)
    {
        _RayStart[0] = (float)point3d.x;
        _RayStart[1] = (float)point3d.y;
        _RayStart[2] = (float)point3d.z;
        _RayEnd[0] = (float)point3d1.x;
        _RayEnd[1] = (float)point3d1.y;
        _RayEnd[2] = (float)point3d1.z;
        boolean flag = com.maddox.il2.engine.Landscape.cRayHitHQ(_RayStart, _RayEnd, _RayHit);
        point3d2.x = _RayHit[0];
        point3d2.y = _RayHit[1];
        point3d2.z = _RayHit[2];
        if(com.maddox.il2.engine.Landscape.meshMakeMapRay(point3d, point3d1))
        {
            float f = 1.0F;
            if(flag)
            {
                double d = point3d.distance(point3d1);
                double d1 = point3d.distance(point3d2);
                if(d > 0.0D)
                    f = (float)(d1 / d);
            }
            _RayHitD[0] = point3d.x;
            _RayHitD[1] = point3d.y;
            _RayHitD[2] = point3d.z;
            _RayHitD[3] = point3d1.x;
            _RayHitD[4] = point3d1.y;
            _RayHitD[5] = point3d1.z;
            boolean flag1 = false;
            for(java.util.Map.Entry entry = meshMapRay.nextEntry(null); entry != null; entry = meshMapRay.nextEntry(entry))
            {
                com.maddox.il2.engine.ActorLandMesh actorlandmesh = (com.maddox.il2.engine.ActorLandMesh)entry.getKey();
                float f1 = actorlandmesh.cRayHit(_RayHitD);
                if(f1 >= 0.0F && f1 < f)
                {
                    f = f1;
                    flag1 = true;
                }
            }

            meshMapRay.clear();
            if(flag1)
            {
                point3d2.interpolate(point3d, point3d1, f);
                flag = true;
            }
        }
        return flag;
    }

    private static final native void cEQN(float af[]);

    public void setRoadsFunDrawing(boolean flag)
    {
        com.maddox.il2.engine.Landscape.cSetRoadsFunDrawing(flag);
    }

    public static native int getFogAverageRGBA();

    public static native float getDynamicFogAlpha();

    public static native int getDynamicFogRGB();

    public void renderBridgeRoad(com.maddox.il2.engine.Mat mat, int i, int j, int k, int l, int i1, float f)
    {
        com.maddox.il2.engine.Landscape.cRenderBridgeRoad(mat.cppObject(), i, j, k, l, i1, f);
    }

    public void LoadMap(java.lang.String s, int ai[])
        throws java.lang.Exception
    {
        LoadMap(s, ai, true);
    }

    public void LoadMap(java.lang.String s, int ai[], int i, int j)
        throws java.lang.Exception
    {
        LoadMap(s, ai, true, i, j);
    }

    public void UnLoadMap()
    {
        if(meshMapXY != null)
            meshMapXY.clear();
        meshMapXY = null;
        com.maddox.il2.engine.Landscape.cUnloadMap();
    }

    public void ReLoadMap()
        throws java.lang.Exception
    {
        if("".equals(MapName))
        {
            return;
        } else
        {
            LoadMap(MapName, null, false, month, day);
            return;
        }
    }

    private void LoadMap(java.lang.String s, int ai[], boolean flag)
        throws java.lang.Exception
    {
        LoadMap(s, ai, flag, config.month, 15);
    }

    private void LoadMap(java.lang.String s, int ai[], boolean flag, int i, int j)
        throws java.lang.Exception
    {
        month = i;
        day = j;
        if(meshMapXY != null)
            meshMapXY.clear();
        meshMapXY = null;
        int k = 0;
        if(ai != null)
            k = ai.length / 2;
        MapName = s;
        config.set("maps/" + MapName);
        bNoWater = "ICE".equals(config.zutiWaterState);
        com.maddox.il2.ai.World.Sun().resetCalendar();
        com.maddox.il2.ai.World.Sun().setAstronomic(config.declin, month, day, com.maddox.il2.ai.World.getTimeofDay());
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            com.maddox.il2.engine.Landscape.setAstronomic(config.declin, month, day, com.maddox.il2.ai.World.getTimeofDay(), com.maddox.il2.ai.World.Sun().moonPhase);
        if(!com.maddox.il2.engine.Landscape.cLoadMap(s, ai, k, flag))
            throw new RuntimeException("Landscape '" + s + "' loading error");
        else
            return;
    }

    public void cubeFullUpdate()
    {
        com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Actor.getByName("camera");
        if(com.maddox.il2.engine.Actor.isValid(actor))
            preRender((float)actor.pos.getAbsPoint().z, true);
    }

    public void cubeFullUpdate(float f)
    {
        preRender(f, true);
    }

    public int nightTime(float f, int i)
    {
        float f2 = (float)java.lang.Math.toRadians(90 - config.declin);
        float f3 = (float)java.lang.Math.cos(f2);
        float f4 = (float)java.lang.Math.sin(f2);
        float f1 = (float)java.lang.Math.toRadians((config.month * 30 + 15) - 80);
        int j = 0;
        int k = 0;
        do
        {
            float f5 = (6.283185F * f) / 24F;
            float f6 = (float)java.lang.Math.sin(f5);
            float f7 = (float)java.lang.Math.cos(f5);
            float f8 = (float)java.lang.Math.sin((float)java.lang.Math.toRadians(22.5D) * (float)java.lang.Math.sin(f1));
            sunRise.set(f6, f7 * f3 + f8 * f4, f8 * f3 - f7 * f4);
            sunRise.normalize();
            int l = 600;
            if(k + l > i)
                l = i - k;
            if(l == 0)
                break;
            k += l;
            if(sunRise.z < -0.1F)
                j += l;
            f += (float)l / 3600F;
            if(f >= 24F)
                f -= 24F;
        } while(true);
        return j;
    }

    public void preRender(float f, boolean flag)
    {
        com.maddox.il2.engine.Sun sun = com.maddox.il2.ai.World.Sun();
        if(flag || com.maddox.il2.engine.Landscape.cIsCubeUpdated())
        {
            sun.setAstronomic(config.declin, month, day, com.maddox.il2.ai.World.getTimeofDay());
            com.maddox.il2.engine.Landscape.setAstronomic(config.declin, month, day, com.maddox.il2.ai.World.getTimeofDay(), sun.moonPhase);
        }
        _sunmoon[0] = sun.ToSun.x;
        _sunmoon[1] = sun.ToSun.y;
        _sunmoon[2] = sun.ToSun.z;
        _sunmoon[3] = sun.ToMoon.x;
        _sunmoon[4] = sun.ToMoon.y;
        _sunmoon[5] = sun.ToMoon.z;
        com.maddox.il2.engine.Landscape.cPreRender(f / 2.0F, flag, _sunmoon);
    }

    public int render0(boolean flag)
    {
        return com.maddox.il2.engine.Landscape.cRender0(flag ? 1 : 0);
    }

    public void render1(boolean flag)
    {
        com.maddox.il2.engine.Landscape.cRender1(flag ? 1 : 0);
    }

    public int ObjectsReflections_Begin(int i)
    {
        return com.maddox.il2.engine.Landscape.cRefBeg(i);
    }

    public void ObjectsReflections_End()
    {
        com.maddox.il2.engine.Landscape.cRefEnd();
    }

    public static native int getFogRGBA(float f, float f1, float f2);

    public static native int ComputeVisibilityOfLandCells(float f, int i, int j, int k, int l, int i1, int j1, int k1, 
            int l1, int ai[]);

    public static native int Compute2DBoundBoxOfVisibleLand(float f, int i, int j, float af[]);

    public static native void MarkStaticActorsCells(int i, int j, int k, int l, int i1, int ai[]);

    public static native void MarkActorCellWithTrees(int i, int j, int k, int ai[], int l);

    private static native boolean cIsCubeUpdated();

    private static native void cSetRoadsFunDrawing(boolean flag);

    private static native void cPreRender(float f, boolean flag, float af[]);

    private static native int cRender0(int i);

    private static native void cRender1(int i);

    private static native boolean cLoadMap(java.lang.String s, int ai[], int i, boolean flag);

    private static native void cUnloadMap();

    private static native void cRenderBridgeRoad(int i, int j, int k, int l, int i1, int j1, float f);

    private static native int cRefBeg(int i);

    private static native int cRefEnd();

    private static native boolean cRayHitHQ(float af[], float af1[], float af2[]);

    private static native void setAstronomic(int i, int j, int k, float f, float f1);

    public static boolean isExistMeshs()
    {
        return meshMapXY != null;
    }

    public static boolean isExistMesh(int i, int j)
    {
        return meshMapXY.get(j, i) != null;
    }

    public static void meshAdd(com.maddox.il2.engine.ActorLandMesh actorlandmesh)
    {
        double d = actorlandmesh.mesh().visibilityR();
        com.maddox.JGP.Point3d point3d = actorlandmesh.pos.getAbsPoint();
        int i = (int)((point3d.x - d - 200D) / 200D);
        int j = (int)((point3d.x + d + 200D) / 200D);
        int k = (int)((point3d.y - d - 200D) / 200D);
        int l = (int)((point3d.y + d + 200D) / 200D);
        float f = 10000F;
        float f1 = -10000F;
        for(int i1 = k; i1 < l; i1++)
        {
            for(int j1 = i; j1 < j; j1++)
            {
                boolean flag = false;
                double d1 = 25D;
                for(int k1 = 1; k1 < 8; k1++)
                {
                    double d2 = (double)i1 * 200D + (double)k1 * d1;
                    for(int l1 = 1; l1 < 8; l1++)
                    {
                        double d3 = (double)j1 * 200D + (double)l1 * d1;
                        float f6 = actorlandmesh.cHQ((float)d3, (float)d2);
                        if(f6 <= -10000F)
                            continue;
                        flag = true;
                        if(f6 > f1)
                            f1 = f6;
                        if(f6 < f)
                            f = f6;
                    }

                }

                if(!flag)
                    continue;
                if(meshMapXY == null)
                    meshMapXY = new HashMapXY16();
                float f2 = (float)j1 * 200F + 100F;
                float f3 = (float)i1 * 200F + 100F;
                float f4 = com.maddox.il2.engine.Landscape.cHmax(f2, f3);
                float f5 = com.maddox.il2.engine.Landscape.cHmax(f2, f3);
                if(f5 > f1)
                    f1 = f5;
                if(f4 > f)
                    f = f4;
                meshMapXY.put(i1, j1, new MeshCell(actorlandmesh, f, f1));
            }

        }

    }

    private static boolean meshMakeMapRay(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        if(meshMapXY == null)
            return false;
        int i = (int)point3d.x / 200;
        int j = (int)point3d.y / 200;
        int k = java.lang.Math.abs((int)point3d1.x / 200 - i) + java.lang.Math.abs((int)point3d1.y / 200 - j) + 1;
        if(k > 1000)
            return false;
        com.maddox.il2.engine.MeshCell meshcell = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
        if(meshcell != null && com.maddox.il2.engine.Actor.isValid(meshcell.m))
            meshMapRay.put(meshcell.m, meshcell.m);
        if(k > 1)
        {
            byte byte0 = 1;
            if(point3d1.x < point3d.x)
                byte0 = -1;
            byte byte1 = 1;
            if(point3d1.y < point3d.y)
                byte1 = -1;
            if(java.lang.Math.abs(point3d1.x - point3d.x) >= java.lang.Math.abs(point3d1.y - point3d.y))
            {
                double d = java.lang.Math.abs(point3d.y % 200D);
                double d2 = (200D * (point3d1.y - point3d.y)) / java.lang.Math.abs(point3d1.x - point3d.x);
                if(d2 >= 0.0D)
                {
                    for(int l = 1; l < k; l++)
                    {
                        if(d < 200D)
                        {
                            i += byte0;
                            d += d2;
                        } else
                        {
                            j += byte1;
                            d -= 200D;
                        }
                        com.maddox.il2.engine.MeshCell meshcell1 = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
                        if(meshcell1 != null && com.maddox.il2.engine.Actor.isValid(meshcell1.m))
                            meshMapRay.put(meshcell1.m, meshcell1.m);
                    }

                } else
                {
                    for(int i1 = 1; i1 < k; i1++)
                    {
                        if(d > 0.0D)
                        {
                            i += byte0;
                            d += d2;
                        } else
                        {
                            j += byte1;
                            d += 200D;
                        }
                        com.maddox.il2.engine.MeshCell meshcell2 = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
                        if(meshcell2 != null && com.maddox.il2.engine.Actor.isValid(meshcell2.m))
                            meshMapRay.put(meshcell2.m, meshcell2.m);
                    }

                }
            } else
            {
                double d1 = java.lang.Math.abs(point3d.x % 200D);
                double d3 = (200D * (point3d1.x - point3d.x)) / java.lang.Math.abs(point3d1.y - point3d.y);
                if(d3 >= 0.0D)
                {
                    for(int j1 = 1; j1 < k; j1++)
                    {
                        if(d1 < 200D)
                        {
                            j += byte1;
                            d1 += d3;
                        } else
                        {
                            i += byte0;
                            d1 -= 200D;
                        }
                        com.maddox.il2.engine.MeshCell meshcell3 = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
                        if(meshcell3 != null && com.maddox.il2.engine.Actor.isValid(meshcell3.m))
                            meshMapRay.put(meshcell3.m, meshcell3.m);
                    }

                } else
                {
                    for(int k1 = 1; k1 < k; k1++)
                    {
                        if(d1 > 0.0D)
                        {
                            j += byte1;
                            d1 += d3;
                        } else
                        {
                            i += byte0;
                            d1 += 200D;
                        }
                        com.maddox.il2.engine.MeshCell meshcell4 = (com.maddox.il2.engine.MeshCell)meshMapXY.get(j, i);
                        if(meshcell4 != null && com.maddox.il2.engine.Actor.isValid(meshcell4.m))
                            meshMapRay.put(meshcell4.m, meshcell4.m);
                    }

                }
            }
        }
        return !meshMapRay.isEmpty();
    }

    private int month;
    private int day;
    public com.maddox.il2.engine.LandConf config;
    public static final float WoodH = 16F;
    public static final int TILE = 8;
    public static final int TILEMASK = 7;
    private static boolean bNoWater = false;
    static final int CH_RATIO = 4;
    static final int CTILE = 32;
    static final int MTILE = 256;
    static final int MTILEMASK = 255;
    public static final float PixelSize = 200F;
    public static final float PixPerMeter = 0.005F;
    private static float EQNBuf[] = new float[4];
    private static double EQNBufD[] = new double[4];
    private static float _RayStart[] = new float[3];
    private static float _RayEnd[] = new float[3];
    private static float _RayHit[] = new float[3];
    private static double _RayHitD[] = new double[6];
    java.lang.String MapName;
    private com.maddox.JGP.Vector3f sunRise;
    private static float _sunmoon[] = new float[6];
    public static final int MESH_STEP = 200;
    public static final float MESH_STEP_MUL = 0.005F;
    private static com.maddox.util.HashMapXY16 meshMapXY;
    private static com.maddox.util.HashMapExt meshMapRay = new HashMapExt();

}
