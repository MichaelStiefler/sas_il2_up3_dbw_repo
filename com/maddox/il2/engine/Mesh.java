// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Mesh.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;

// Referenced classes of package com.maddox.il2.engine:
//            GObj, GObjException, Mat, Orient, 
//            Loc, Config, Pre, HierMesh

public class Mesh extends com.maddox.il2.engine.GObj
{

    public boolean isAllowsSimpleRendering()
    {
        return bAllowsSimpleRendering;
    }

    public void setPos(com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient)
    {
        SetPosXYZATK(cppObj, point3d.x, point3d.y, point3d.z, orient.getAzimut(), orient.getTangage(), orient.getKren());
    }

    public void setPos(com.maddox.il2.engine.Loc loc)
    {
        com.maddox.JGP.Point3d point3d = loc.getPoint();
        com.maddox.il2.engine.Orient orient = loc.getOrient();
        SetPosXYZATK(cppObj, point3d.x, point3d.y, point3d.z, orient.getAzimut(), orient.getTangage(), orient.getKren());
    }

    public final int preRender(com.maddox.JGP.Point3d point3d)
    {
        return PreRenderXYZ(cppObj, point3d.x, point3d.y, point3d.z);
    }

    public int preRender()
    {
        return PreRender(cppObj);
    }

    public void setFastShadowVisibility(int i)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            SetFastShadowVisibility(cppObj, i);
    }

    public void render()
    {
        Render(cppObj);
    }

    public void renderShadowProjective()
    {
        RenderShadowProjective(cppObj);
    }

    public void renderShadowVolume()
    {
        RenderShadowVolume(cppObj);
    }

    public void renderShadowVolumeHQ()
    {
        RenderShadowVolumeHQ(cppObj);
    }

    public int detectCollision(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Mesh mesh, com.maddox.il2.engine.Loc loc1)
    {
        loc.get(tmp);
        loc1.get(tmp2);
        return DetectCollision(cppObj, tmp, mesh.cppObj, tmp2);
    }

    public float detectCollisionLine(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        loc.get(tmp);
        point3d.get(ad1);
        point3d1.get(ad2);
        return DetectCollisionLine(cppObj, tmp, ad1, ad2);
    }

    public int detectCollisionLineMulti(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        loc.get(tmp);
        point3d.get(ad1);
        point3d1.get(ad2);
        return DetectCollisionLineMulti(cppObj, tmp, ad1, ad2);
    }

    public static native java.lang.String collisionNameMulti(int i, int j);

    public static native float collisionDistMulti(int i);

    public float detectCollisionQuad(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        loc.get(tmp);
        tmp2[0] = point3d.x;
        tmp2[1] = point3d.y;
        tmp2[2] = point3d.z;
        tmp2[3] = point3d1.x;
        tmp2[4] = point3d1.y;
        tmp2[5] = point3d1.z;
        tmp2[6] = point3d2.x;
        tmp2[7] = point3d2.y;
        tmp2[8] = point3d2.z;
        tmp2[9] = point3d3.x;
        tmp2[10] = point3d3.y;
        tmp2[11] = point3d3.z;
        return DetectCollisionQuad(cppObj, tmp, tmp2);
    }

    public int detectCollision_Quad_Multi(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        loc.get(tmp);
        tmp2[0] = point3d.x;
        tmp2[1] = point3d.y;
        tmp2[2] = point3d.z;
        tmp2[3] = point3d1.x;
        tmp2[4] = point3d1.y;
        tmp2[5] = point3d1.z;
        tmp2[6] = point3d2.x;
        tmp2[7] = point3d2.y;
        tmp2[8] = point3d2.z;
        tmp2[9] = point3d3.x;
        tmp2[10] = point3d3.y;
        tmp2[11] = point3d3.z;
        return DetectCollisionQuadMultiM(cppObj, tmp, tmp2);
    }

    public int detectCollisionPoint(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d)
    {
        loc.get(tmp);
        point3d.get(ad);
        return DetectCollisionPoint(cppObj, tmp, ad);
    }

    public float detectCollisionPlane(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, double d)
    {
        loc.get(tmp);
        vector3d.get(tmp2);
        tmp2[3] = d;
        return DetectCollisionPlane(cppObj, tmp, tmp2);
    }

    public static native java.lang.String collisionChunk(int i);

    public void setScale(float f)
    {
        SetScale(cppObj, f);
    }

    public float scale()
    {
        return Scale(cppObj);
    }

    public void setScaleXYZ(float f, float f1, float f2)
    {
        SetScaleXYZ(cppObj, f, f1, f2);
    }

    public void scaleXYZ(float af1[])
    {
        ScaleXYZ(cppObj, af1);
    }

    public float visibilityR()
    {
        return visibilityR;
    }

    public float collisionR()
    {
        return collisionR;
    }

    public float getUniformMaxDist()
    {
        return GetUniformMaxDist(cppObj);
    }

    public void setCurChunk(int i)
    {
    }

    public int getCurChunk()
    {
        return 0;
    }

    public int frames()
    {
        return Frames(cppObj);
    }

    public void setFrame(int i)
    {
        SetFrame(cppObj, i);
    }

    public void setFrame(int i, int j, float f)
    {
        SetFrame(cppObj, i, j, f);
    }

    public void setFrameFromRange(int i, int j, float f)
    {
        SetFrameFromRange(cppObj, i, j, f);
    }

    public void getBoundBox(float af1[])
    {
        if(af1.length < 6)
        {
            throw new GObjException("Internal error: wrong array size");
        } else
        {
            GetBoundBox(cppObj, af1);
            return;
        }
    }

    public void getDimensions(com.maddox.JGP.Vector3f vector3f)
    {
        getBoundBox(dimns);
        vector3f.x = dimns[3] - dimns[0];
        vector3f.y = dimns[4] - dimns[1];
        vector3f.z = dimns[5] - dimns[2];
    }

    public int hooks()
    {
        return Hooks(cppObj);
    }

    public int hookFind(java.lang.String s)
    {
        return HookFind(cppObj, s);
    }

    public java.lang.String hookName(int i)
    {
        return HookName(cppObj, i);
    }

    public void hookMatrix(int i, com.maddox.JGP.Matrix4d matrix4d)
    {
        HookMatrix(cppObj, i, tmp);
        matrix4d.set(tmp);
    }

    public int hookFaceCollisFind(com.maddox.JGP.Matrix4d matrix4d, int ai[], com.maddox.JGP.Matrix4d matrix4d1)
    {
        return hookFaceFind(matrix4d, matrix4d1);
    }

    public int hookFaceFind(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        tmp[0] = matrix4d.m00;
        tmp[4] = matrix4d.m01;
        tmp[8] = matrix4d.m02;
        tmp[12] = matrix4d.m03;
        tmp[1] = matrix4d.m10;
        tmp[5] = matrix4d.m11;
        tmp[9] = matrix4d.m12;
        tmp[13] = matrix4d.m13;
        tmp[2] = matrix4d.m20;
        tmp[6] = matrix4d.m21;
        tmp[10] = matrix4d.m22;
        tmp[14] = matrix4d.m23;
        tmp[3] = matrix4d.m30;
        tmp[7] = matrix4d.m31;
        tmp[11] = matrix4d.m32;
        tmp[15] = matrix4d.m33;
        int i = HookFaceFind(cppObj, tmp, tmp2);
        if(i != -1)
            matrix4d1.set(tmp2);
        return i;
    }

    public int hookFaceFind(com.maddox.JGP.Matrix4d matrix4d, int ai[], com.maddox.JGP.Matrix4d matrix4d1)
    {
        return hookFaceFind(matrix4d, matrix4d1);
    }

    public void hookFaceMatrix(int i, int j, com.maddox.JGP.Matrix4d matrix4d)
    {
        HookFaceMatrix(cppObj, i, j, tmp);
        matrix4d.set(tmp);
    }

    public int materials()
    {
        return Materials(cppObj);
    }

    public int materialFind(java.lang.String s, int i, int j)
    {
        return MaterialFind(cppObj, s, i, j);
    }

    public int materialFind(java.lang.String s, int i)
    {
        return MaterialFind(cppObj, s, i, -1);
    }

    public int materialFind(java.lang.String s)
    {
        return MaterialFind(cppObj, s, 0, -1);
    }

    public com.maddox.il2.engine.Mat material(int i)
    {
        return (com.maddox.il2.engine.Mat)Material(cppObj, i);
    }

    public void materialReplace(int i, com.maddox.il2.engine.Mat mat)
    {
        MaterialReplace(cppObj, i, mat.cppObject());
    }

    public void materialReplace(int i, java.lang.String s)
    {
        MaterialReplace(cppObj, i, s);
    }

    public void materialReplace(java.lang.String s, com.maddox.il2.engine.Mat mat)
    {
        MaterialReplace(cppObj, s, mat.cppObject());
    }

    public void materialReplaceToNull(java.lang.String s)
    {
        MaterialReplace(cppObj, s, 0);
    }

    public void materialReplace(java.lang.String s, java.lang.String s1)
    {
        MaterialReplace(cppObj, s, s1);
    }

    public void makeAllMaterialsDarker(float f, float f1)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        int i = materials();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Mat mat = material(j);
            if(mat == null)
                continue;
            java.lang.String s = mat.Name();
            if(s == null || s.length() < 5 || !s.toUpperCase().endsWith(".MAT"))
                continue;
            java.lang.String s1 = s.substring(0, s.length() - 4) + "_dark.mat";
            com.maddox.il2.engine.Mat mat1 = null;
            if(com.maddox.il2.engine.Mat.Exist(s1))
                mat1 = (com.maddox.il2.engine.Mat)com.maddox.il2.engine.Mat.Get(s1);
            if(mat1 == null)
            {
                mat1 = (com.maddox.il2.engine.Mat)mat.Clone();
                if(mat1 == null)
                    continue;
                mat1.Rename(s1);
                mat1.set((byte)20, mat1.get((byte)20) * f);
                mat1.set((byte)21, mat1.get((byte)21) * f1);
                mat1.set((byte)22, 0.0F);
                mat1.set((byte)23, 0.0F);
                mat1.set((byte)24, 0.0F);
            }
            materialReplace(j, mat1);
        }

    }

    public static boolean isSimilar(com.maddox.il2.engine.Mesh mesh, com.maddox.il2.engine.Mesh mesh1)
    {
        if(mesh == mesh1)
            return true;
        else
            return com.maddox.il2.engine.Mesh.IsSimilar(mesh.cppObj, mesh1.cppObj) != 0;
    }

    protected float heightMapMeshGetHeight(double d, double d1)
    {
        return HeightMapMeshGetHeight(cppObj, d, d1);
    }

    protected boolean heightMapMeshGetNormal(double d, double d1, float af1[])
    {
        return HeightMapMeshGetNormal(cppObj, d, d1, af1) == 1;
    }

    protected boolean heightMapMeshGetPlane(double d, double d1, double ad4[])
    {
        return HeightMapMeshGetPlane(cppObj, d, d1, ad4) == 1;
    }

    protected float heightMapMeshGetRayHit(double ad4[])
    {
        return HeightMapMeshGetRayHit(cppObj, ad4);
    }

    public void destroy()
    {
        if(cppObj != 0)
        {
            com.maddox.il2.engine.Mesh.Finalize(cppObj);
            cppObj = 0;
        }
    }

    protected Mesh(int i)
    {
        super(i);
        bAllowsSimpleRendering = false;
    }

    public Mesh(java.lang.String s)
    {
        super(0);
        bAllowsSimpleRendering = false;
        if(s == null)
            throw new GObjException("Meshname is empty");
        cppObj = Load(s);
        if(cppObj == 0)
            throw new GObjException("Mesh " + s + " not created");
        collisionR = CollisionR(cppObj);
        visibilityR = VisibilityR(cppObj);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            bAllowsSimpleRendering = IsAllowsSimpleRendering(cppObj) != 0;
        com.maddox.il2.engine.Pre.load(s);
    }

    public Mesh(com.maddox.il2.engine.HierMesh hiermesh)
    {
        super(0);
        bAllowsSimpleRendering = false;
        cppObj = CreateMeshFromHiermeshChunk(hiermesh.cppObj);
        if(cppObj == 0)
            throw new GObjException("Mesh not created from hiermesh");
        collisionR = CollisionR(cppObj);
        visibilityR = VisibilityR(cppObj);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            bAllowsSimpleRendering = IsAllowsSimpleRendering(cppObj) != 0;
        com.maddox.il2.engine.Pre.load(GetFileName(cppObj));
    }

    private native int Load(java.lang.String s);

    private native int CreateMeshFromHiermeshChunk(int i);

    private native java.lang.String GetFileName(int i);

    private native void GetBoundBox(int i, float af1[]);

    private native void SetPosXYZATK(int i, double d, double d1, double d2, 
            float f, float f1, float f2);

    private native int PreRenderXYZ(int i, double d, double d1, double d2);

    private native int PreRender(int i);

    private native void SetFastShadowVisibility(int i, int j);

    private native void Render(int i);

    private native void RenderShadowProjective(int i);

    private native void RenderShadowVolume(int i);

    private native void RenderShadowVolumeHQ(int i);

    private native int DetectCollision(int i, double ad4[], int j, double ad5[]);

    private native float DetectCollisionLine(int i, double ad4[], double ad5[], double ad6[]);

    private native int DetectCollisionLineMulti(int i, double ad4[], double ad5[], double ad6[]);

    private native float DetectCollisionQuad(int i, double ad4[], double ad5[]);

    private native int DetectCollisionQuadMultiM(int i, double ad4[], double ad5[]);

    private native int DetectCollisionPoint(int i, double ad4[], double ad5[]);

    private native float DetectCollisionPlane(int i, double ad4[], double ad5[]);

    private native void SetScale(int i, float f);

    private native float Scale(int i);

    private native void SetScaleXYZ(int i, float f, float f1, float f2);

    private native void ScaleXYZ(int i, float af1[]);

    private native float VisibilityR(int i);

    private native float CollisionR(int i);

    private native float GetUniformMaxDist(int i);

    private native int Frames(int i);

    private native void SetFrame(int i, int j);

    private native void SetFrame(int i, int j, int k, float f);

    private native void SetFrameFromRange(int i, int j, int k, float f);

    private native int Hooks(int i);

    private native int HookFind(int i, java.lang.String s);

    private native java.lang.String HookName(int i, int j);

    private native void HookMatrix(int i, int j, double ad4[]);

    private native int HookFaceFind(int i, double ad4[], double ad5[]);

    private native void HookFaceMatrix(int i, int j, int k, double ad4[]);

    private native int Materials(int i);

    private native int MaterialFind(int i, java.lang.String s, int j, int k);

    private native java.lang.Object Material(int i, int j);

    private native void MaterialReplace(int i, int j, int k);

    private native void MaterialReplace(int i, int j, java.lang.String s);

    private native void MaterialReplace(int i, java.lang.String s, int j);

    private native void MaterialReplace(int i, java.lang.String s, java.lang.String s1);

    private static native int IsSimilar(int i, int j);

    private native int IsAllowsSimpleRendering(int i);

    private native float HeightMapMeshGetHeight(int i, double d, double d1);

    private native int HeightMapMeshGetNormal(int i, double d, double d1, float af1[]);

    private native int HeightMapMeshGetPlane(int i, double d, double d1, double ad4[]);

    private native float HeightMapMeshGetRayHit(int i, double ad4[]);

    protected static float af[] = new float[3];
    protected static double ad[] = new double[3];
    protected static double ad1[] = new double[3];
    protected static double ad2[] = new double[3];
    protected static double ad3[] = new double[3];
    protected static double tmp[] = new double[16];
    protected static double tmp2[] = new double[16];
    private static float dimns[] = new float[6];
    protected boolean bAllowsSimpleRendering;
    protected float visibilityR;
    protected float collisionR;

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }
}
