// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MeshShared.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.util.HashMapExt;

// Referenced classes of package com.maddox.il2.engine:
//            Mesh, Loc, Orient, Mat

public class MeshShared extends com.maddox.il2.engine.Mesh
{

    public void materialReplace(int i, com.maddox.il2.engine.Mat mat)
    {
    }

    public void materialReplace(int i, java.lang.String s)
    {
    }

    public void materialReplace(java.lang.String s, com.maddox.il2.engine.Mat mat)
    {
    }

    public void materialReplace(java.lang.String s, java.lang.String s1)
    {
    }

    public static com.maddox.il2.engine.MeshShared get(java.lang.String s)
    {
        com.maddox.il2.engine.MeshShared meshshared = (com.maddox.il2.engine.MeshShared)shared.get(s);
        if(meshshared == null)
        {
            meshshared = new MeshShared(s);
            shared.put(s, meshshared);
        }
        return meshshared;
    }

    public MeshShared(java.lang.String s)
    {
        super(0);
        com.maddox.il2.engine.Mesh mesh = (com.maddox.il2.engine.Mesh)meshes.get(s);
        if(mesh == null)
        {
            mesh = new Mesh(s);
            meshes.put(s, mesh);
        }
        cppObj = mesh.cppObject();
        collisionR = mesh.collisionR();
        visibilityR = mesh.visibilityR();
        bAllowsSimpleRendering = mesh.bAllowsSimpleRendering;
    }

    protected void finalize()
    {
    }

    public void destroy()
    {
    }

    public static void clearAll()
    {
        shared.clear();
        meshes.clear();
    }

    public static void clearRenderArray()
    {
        arraySize = 0;
    }

    public static int sizeRenderArray()
    {
        return arraySize;
    }

    public boolean putToRenderArray(com.maddox.il2.engine.Loc loc)
    {
        if(!bAllowsSimpleRendering || arraySize >= 2048)
        {
            return false;
        } else
        {
            arrayCppObjs[arraySize] = cppObj;
            com.maddox.JGP.Point3d point3d = loc.getPoint();
            com.maddox.il2.engine.Orient orient = loc.getOrient();
            int i = 3 * arraySize;
            arrayXYZ[i + 0] = point3d.x;
            arrayXYZ[i + 1] = point3d.y;
            arrayXYZ[i + 2] = point3d.z;
            arrayATK[i + 0] = orient.getAzimut();
            arrayATK[i + 1] = orient.getTangage();
            arrayATK[i + 2] = orient.getKren();
            arraySize++;
            return true;
        }
    }

    public static void renderArray(boolean flag)
    {
        if(arraySize > 0)
            com.maddox.il2.engine.MeshShared.RenderArray(flag, arraySize, arrayCppObjs, arrayXYZ, arrayATK);
        arraySize = 0;
    }

    public static void renderArrayShadowProjective()
    {
        if(arraySize > 0)
            com.maddox.il2.engine.MeshShared.RenderArrayShadowProjective(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
        arraySize = 0;
    }

    public static void renderArrayShadowVolume()
    {
        if(arraySize > 0)
            com.maddox.il2.engine.MeshShared.RenderArrayShadowVolume(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
        arraySize = 0;
    }

    public static void renderArrayShadowVolumeHQ()
    {
        if(arraySize > 0)
            com.maddox.il2.engine.MeshShared.RenderArrayShadowVolumeHQ(arraySize, arrayCppObjs, arrayXYZ, arrayATK);
        arraySize = 0;
    }

    private static native void RenderArray(boolean flag, int i, int ai[], double ad[], float af[]);

    private static native void RenderArrayShadowProjective(int i, int ai[], double ad[], float af[]);

    private static native void RenderArrayShadowVolume(int i, int ai[], double ad[], float af[]);

    private static native void RenderArrayShadowVolumeHQ(int i, int ai[], double ad[], float af[]);

    private static com.maddox.util.HashMapExt shared = new HashMapExt();
    private static com.maddox.util.HashMapExt meshes = new HashMapExt();
    public static final int MAX_RENDER_ARRAY = 2048;
    private static int arrayCppObjs[] = new int[2048];
    private static double arrayXYZ[] = new double[6144];
    private static float arrayATK[] = new float[6144];
    private static int arraySize = 0;

}
