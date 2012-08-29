// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HierMesh.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.engine:
//            Mesh, Mat, GObjException, Loc, 
//            Orient, Pre, GObj

public class HierMesh extends com.maddox.il2.engine.Mesh
{
    class ChunkState
    {

        public boolean bExist;
        public int indx;
        public boolean bVisible;
        public float x;
        public float y;
        public float z;
        public float yaw;
        public float pitch;
        public float roll;

        public ChunkState(java.lang.String s)
        {
            try
            {
                indx = chunkFind(s);
            }
            catch(java.lang.Exception exception)
            {
                bExist = false;
                return;
            }
            bExist = true;
            setCurChunk(indx);
            bVisible = isChunkVisible();
            getChunkLocObj(com.maddox.il2.engine.HierMesh._chunkLoc);
            x = (float)com.maddox.il2.engine.HierMesh._chunkLoc.getX();
            y = (float)com.maddox.il2.engine.HierMesh._chunkLoc.getY();
            z = (float)com.maddox.il2.engine.HierMesh._chunkLoc.getZ();
            yaw = com.maddox.il2.engine.HierMesh._chunkLoc.getOrient().getYaw();
            pitch = com.maddox.il2.engine.HierMesh._chunkLoc.getOrient().getPitch();
            roll = com.maddox.il2.engine.HierMesh._chunkLoc.getOrient().getRoll();
        }
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

    public int preRender()
    {
        return PreRender(cppObj);
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

    public void renderChunkMirror(double ad[], double ad1[], double ad2[])
    {
        RenderChunkMirror(cppObj, ad, ad1, ad2);
    }

    public static void renderShadowPairs(java.util.ArrayList arraylist)
    {
        int i = arraylist.size();
        if(i == 0)
            return;
        if(shadowPairs.length < i)
            shadowPairs = new int[(i / 2 + 1) * 2];
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.HierMesh hiermesh = (com.maddox.il2.engine.HierMesh)arraylist.get(j);
            shadowPairs[j] = hiermesh.cppObject();
        }

        com.maddox.il2.engine.HierMesh.RenderShadowPairs(i / 2, shadowPairs);
    }

    public int detectCollision(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.engine.Loc loc1)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        loc1.get(com.maddox.il2.engine.Mesh.tmp2);
        return DetectCollision(cppObj, com.maddox.il2.engine.Mesh.tmp, hiermesh.cppObj, com.maddox.il2.engine.Mesh.tmp2);
    }

    public int detectCollision(com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Mesh mesh, com.maddox.il2.engine.Loc loc1)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        loc1.get(com.maddox.il2.engine.Mesh.tmp2);
        return DetectCollisionMesh(cppObj, com.maddox.il2.engine.Mesh.tmp, mesh.cppObj, com.maddox.il2.engine.Mesh.tmp2);
    }

    public float detectCollisionLine(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        point3d.get(com.maddox.il2.engine.Mesh.ad);
        point3d1.get(com.maddox.il2.engine.Mesh.tmp2);
        return DetectCollisionLine(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.ad, com.maddox.il2.engine.Mesh.tmp2);
    }

    public int detectCollisionLineMulti(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        point3d.get(com.maddox.il2.engine.Mesh.ad);
        point3d1.get(com.maddox.il2.engine.Mesh.tmp2);
        return DetectCollisionLineMulti(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.ad, com.maddox.il2.engine.Mesh.tmp2);
    }

    public float detectCollisionQuad(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        com.maddox.il2.engine.Mesh.tmp2[0] = point3d.x;
        com.maddox.il2.engine.Mesh.tmp2[1] = point3d.y;
        com.maddox.il2.engine.Mesh.tmp2[2] = point3d.z;
        com.maddox.il2.engine.Mesh.tmp2[3] = point3d1.x;
        com.maddox.il2.engine.Mesh.tmp2[4] = point3d1.y;
        com.maddox.il2.engine.Mesh.tmp2[5] = point3d1.z;
        com.maddox.il2.engine.Mesh.tmp2[6] = point3d2.x;
        com.maddox.il2.engine.Mesh.tmp2[7] = point3d2.y;
        com.maddox.il2.engine.Mesh.tmp2[8] = point3d2.z;
        com.maddox.il2.engine.Mesh.tmp2[9] = point3d3.x;
        com.maddox.il2.engine.Mesh.tmp2[10] = point3d3.y;
        com.maddox.il2.engine.Mesh.tmp2[11] = point3d3.z;
        return DetectCollisionQuad(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.tmp2);
    }

    public int detectCollision_Quad_Multi(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1, com.maddox.JGP.Point3d point3d2, com.maddox.JGP.Point3d point3d3)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        com.maddox.il2.engine.Mesh.tmp2[0] = point3d.x;
        com.maddox.il2.engine.Mesh.tmp2[1] = point3d.y;
        com.maddox.il2.engine.Mesh.tmp2[2] = point3d.z;
        com.maddox.il2.engine.Mesh.tmp2[3] = point3d1.x;
        com.maddox.il2.engine.Mesh.tmp2[4] = point3d1.y;
        com.maddox.il2.engine.Mesh.tmp2[5] = point3d1.z;
        com.maddox.il2.engine.Mesh.tmp2[6] = point3d2.x;
        com.maddox.il2.engine.Mesh.tmp2[7] = point3d2.y;
        com.maddox.il2.engine.Mesh.tmp2[8] = point3d2.z;
        com.maddox.il2.engine.Mesh.tmp2[9] = point3d3.x;
        com.maddox.il2.engine.Mesh.tmp2[10] = point3d3.y;
        com.maddox.il2.engine.Mesh.tmp2[11] = point3d3.z;
        return DetectCollisionQuadMultiH(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.tmp2);
    }

    public int detectCollisionPoint(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Point3d point3d)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        point3d.get(com.maddox.il2.engine.Mesh.ad);
        return DetectCollisionPoint(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.ad);
    }

    public float detectCollisionPlane(com.maddox.il2.engine.Loc loc, com.maddox.JGP.Vector3d vector3d, double d)
    {
        loc.get(com.maddox.il2.engine.Mesh.tmp);
        vector3d.get(com.maddox.il2.engine.Mesh.tmp2);
        com.maddox.il2.engine.Mesh.tmp2[3] = d;
        return DetectCollisionPlane(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.tmp2);
    }

    public void setScale(float f)
    {
    }

    public float scale()
    {
        return 1.0F;
    }

    public void setScaleXYZ(float f, float f1, float f2)
    {
    }

    public void scaleXYZ(float af[])
    {
        af[0] = af[1] = af[2] = 1.0F;
    }

    public float getUniformMaxDist()
    {
        return GetUniformMaxDist(cppObj);
    }

    public int chunks()
    {
        return Chunks(cppObj);
    }

    public boolean isChunkVisible(java.lang.String s)
    {
        com.maddox.il2.engine.ChunkState chunkstate = chunkState(s);
        if(chunkstate == null)
            return false;
        else
            return chunkstate.bVisible;
    }

    public void chunkVisible(java.lang.String s, boolean flag)
    {
        com.maddox.il2.engine.ChunkState chunkstate = chunkState(s);
        if(chunkstate == null)
            return;
        if(chunkstate.bVisible == flag)
        {
            return;
        } else
        {
            chunkstate.bVisible = flag;
            setCurChunk(chunkstate.indx);
            SetChunkVisibility(cppObj, flag ? 1 : 0);
            return;
        }
    }

    public void chunkSetAngles(java.lang.String s, float af[])
    {
        chunkSetAngles(s, af[0], af[1], af[2]);
    }

    public void chunkSetAngles(java.lang.String s, float f, float f1, float f2)
    {
        com.maddox.il2.engine.ChunkState chunkstate = chunkState(s);
        if(chunkstate == null)
            return;
        if(chunkstate.yaw == f && chunkstate.pitch == f1 && chunkstate.roll == f2)
        {
            return;
        } else
        {
            _chunkAngles[0] = chunkstate.yaw = f;
            _chunkAngles[1] = chunkstate.pitch = f1;
            _chunkAngles[2] = chunkstate.roll = f2;
            setCurChunk(chunkstate.indx);
            SetChunkAngles(cppObj, _chunkAngles);
            return;
        }
    }

    public void chunkSetLocate(java.lang.String s, float af[], float af1[])
    {
        com.maddox.il2.engine.ChunkState chunkstate = chunkState(s);
        if(chunkstate == null)
            return;
        if(chunkstate.yaw == af1[0] && chunkstate.pitch == af1[1] && chunkstate.roll == af1[2] && chunkstate.x == af[0] && chunkstate.y == af[1] && chunkstate.z == af[2])
        {
            return;
        } else
        {
            chunkstate.yaw = af1[0];
            chunkstate.pitch = af1[1];
            chunkstate.roll = af1[2];
            chunkstate.x = af[0];
            chunkstate.y = af[1];
            chunkstate.z = af[2];
            setCurChunk(chunkstate.indx);
            SetChunkLocate(cppObj, af, af1);
            return;
        }
    }

    private com.maddox.il2.engine.ChunkState chunkState(java.lang.String s)
    {
        if(chunkMap == null)
            chunkMap = new HashMapExt();
        com.maddox.il2.engine.ChunkState chunkstate = (com.maddox.il2.engine.ChunkState)chunkMap.get(s);
        if(chunkstate == null)
        {
            chunkstate = new ChunkState(s);
            chunkMap.put(s, chunkstate);
        }
        if(chunkstate.bExist)
            return chunkstate;
        else
            return null;
    }

    public void setCurChunk(int i)
    {
        curchunk = i;
        SetCurChunk(cppObj, i);
    }

    public void setCurChunk(java.lang.String s)
    {
        curchunk = SetCurChunkByName(cppObj, s);
    }

    public int getCurChunk()
    {
        return curchunk;
    }

    public int chunkFind(java.lang.String s)
    {
        return ChunkFind(cppObj, s);
    }

    public int chunkFindCheck(java.lang.String s)
    {
        return ChunkFindCheck(cppObj, s);
    }

    public java.lang.String chunkName()
    {
        return ChunkName(cppObj);
    }

    public float getChunkVisibilityR()
    {
        return GetChunkVisibilityR(cppObj);
    }

    public boolean isChunkVisible()
    {
        return GetChunkVisibility(cppObj) != 0;
    }

    public void chunkVisible(boolean flag)
    {
        SetChunkVisibility(cppObj, flag ? 1 : 0);
    }

    public void chunkSetAngles(float af[])
    {
        SetChunkAngles(cppObj, af);
    }

    public void chunkSetLocate(float af[], float af1[])
    {
        SetChunkLocate(cppObj, af, af1);
    }

    public void getChunkLTM(com.maddox.JGP.Matrix4d matrix4d)
    {
        GetChunkLTM(cppObj, com.maddox.il2.engine.Mesh.tmp);
        matrix4d.set(com.maddox.il2.engine.Mesh.tmp);
    }

    public void getChunkCurVisBoundBox(com.maddox.JGP.Point3f point3f, com.maddox.JGP.Point3f point3f1)
    {
        GetChunkCurVisBoundBox(cppObj, arrFloat6);
        point3f.set(arrFloat6[0], arrFloat6[1], arrFloat6[2]);
        point3f1.set(arrFloat6[3], arrFloat6[4], arrFloat6[5]);
    }

    public void getChunkLocObj(com.maddox.il2.engine.Loc loc)
    {
        getChunkLTM(m4);
        m4.getEulers(Eul);
        Eul[0] *= -57.299999237060547D;
        Eul[1] *= -57.299999237060547D;
        Eul[2] *= 57.299999237060547D;
        loc.set(m4.m03, m4.m13, m4.m23, (float)Eul[0], (float)Eul[1], (float)Eul[2]);
    }

    public float getChunkMass()
    {
        return 0.0F;
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

    public int hooks()
    {
        return Hooks(cppObj);
    }

    public int hookFind(java.lang.String s)
    {
        return HookFind(cppObj, s);
    }

    public int hookParentChunk(java.lang.String s)
    {
        int i = HookFind(cppObj, s) >> 16;
        if(i <= 0)
            return -1;
        else
            return i - 1;
    }

    public java.lang.String hookName(int i)
    {
        return HookName(cppObj, i);
    }

    public void hookMatrix(int i, com.maddox.JGP.Matrix4d matrix4d)
    {
        HookMatrix(cppObj, i, com.maddox.il2.engine.Mesh.tmp);
        matrix4d.set(com.maddox.il2.engine.Mesh.tmp);
    }

    public int chunkByHookNamed(int i)
    {
        return ChunkByHookNamed(cppObj, i);
    }

    public int hookFaceCollisFind(com.maddox.JGP.Matrix4d matrix4d, int ai[], com.maddox.JGP.Matrix4d matrix4d1)
    {
        com.maddox.il2.engine.Mesh.tmp[0] = matrix4d.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = matrix4d.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = matrix4d.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = matrix4d.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = matrix4d.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = matrix4d.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = matrix4d.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = matrix4d.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = matrix4d.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = matrix4d.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = matrix4d.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = matrix4d.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = matrix4d.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = matrix4d.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = matrix4d.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = matrix4d.m33;
        int i = HookFaceCollisFind(cppObj, com.maddox.il2.engine.Mesh.tmp, ai, com.maddox.il2.engine.Mesh.tmp2);
        if(i != -1)
            matrix4d1.set(com.maddox.il2.engine.Mesh.tmp2);
        return i;
    }

    public int hookChunkFaceFind(com.maddox.JGP.Matrix4d matrix4d, com.maddox.JGP.Matrix4d matrix4d1)
    {
        com.maddox.il2.engine.Mesh.tmp[0] = matrix4d.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = matrix4d.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = matrix4d.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = matrix4d.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = matrix4d.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = matrix4d.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = matrix4d.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = matrix4d.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = matrix4d.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = matrix4d.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = matrix4d.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = matrix4d.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = matrix4d.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = matrix4d.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = matrix4d.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = matrix4d.m33;
        int i = HookChunkFaceFind(cppObj, com.maddox.il2.engine.Mesh.tmp, com.maddox.il2.engine.Mesh.tmp2);
        if(i != -1)
            matrix4d1.set(com.maddox.il2.engine.Mesh.tmp2);
        return i;
    }

    public int hookFaceFind(com.maddox.JGP.Matrix4d matrix4d, int ai[], com.maddox.JGP.Matrix4d matrix4d1)
    {
        com.maddox.il2.engine.Mesh.tmp[0] = matrix4d.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = matrix4d.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = matrix4d.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = matrix4d.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = matrix4d.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = matrix4d.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = matrix4d.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = matrix4d.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = matrix4d.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = matrix4d.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = matrix4d.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = matrix4d.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = matrix4d.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = matrix4d.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = matrix4d.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = matrix4d.m33;
        int i = HookFaceFind(cppObj, com.maddox.il2.engine.Mesh.tmp, ai, com.maddox.il2.engine.Mesh.tmp2);
        if(i != -1)
            matrix4d1.set(com.maddox.il2.engine.Mesh.tmp2);
        return i;
    }

    public void hookFaceMatrix(int i, int j, com.maddox.JGP.Matrix4d matrix4d)
    {
        HookFaceMatrix(cppObj, i, j, com.maddox.il2.engine.Mesh.tmp);
        matrix4d.set(com.maddox.il2.engine.Mesh.tmp);
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

    public int materialFindInChunk(java.lang.String s, int i)
    {
        return MaterialFindInChunk(cppObj, s, i);
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

    public int[] hideSubTrees(java.lang.String s)
    {
        chunkMap = null;
        return HideChunksInSubtrees(cppObj, s);
    }

    public int[] getSubTrees(java.lang.String s)
    {
        chunkMap = null;
        return GetChunksInSubtrees(cppObj, s);
    }

    public int[] getSubTreesSpec(java.lang.String s)
    {
        chunkMap = null;
        return GetChunksInSubtreesSpec(cppObj, s);
    }

    public float poseCRC()
    {
        return PoseCRC(cppObj);
    }

    public boolean isVisualRayHit(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d, double d, double d1, com.maddox.JGP.Matrix4d matrix4d)
    {
        com.maddox.il2.engine.Mesh.tmp[0] = matrix4d.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = matrix4d.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = matrix4d.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = matrix4d.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = matrix4d.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = matrix4d.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = matrix4d.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = matrix4d.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = matrix4d.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = matrix4d.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = matrix4d.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = matrix4d.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = matrix4d.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = matrix4d.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = matrix4d.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = matrix4d.m33;
        arrDouble6[0] = point3d.x;
        arrDouble6[1] = point3d.y;
        arrDouble6[2] = point3d.z;
        arrDouble6[3] = vector3d.x;
        arrDouble6[4] = vector3d.y;
        arrDouble6[5] = vector3d.z;
        return isVisualRayHit(cppObj, arrDouble6, d, d1, com.maddox.il2.engine.Mesh.tmp) != 0;
    }

    public int ApplyDecal_test(float f, float f1, boolean flag, com.maddox.il2.engine.Loc loc, int i)
    {
        loc.getMatrix(m4);
        com.maddox.il2.engine.Mesh.tmp[0] = m4.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = m4.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = m4.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = m4.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = m4.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = m4.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = m4.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = m4.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = m4.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = m4.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = m4.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = m4.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = m4.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = m4.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = m4.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = m4.m33;
        float af[] = new float[3];
        af[0] = 0.0F;
        af[1] = 0.0F;
        af[2] = 0.0F;
        int ai[] = new int[chunks()];
        for(int j = 0; j < ai.length; j++)
            ai[j] = j;

        int k = ApplyDecal(cppObj, rnd.nextInt(0, 2), f, f1, flag ? 1 : 0, com.maddox.il2.engine.Mesh.tmp, af, i, ai, ai.length);
        java.lang.System.out.println("-- applyDec: " + k + "(chIdx:" + i + ")");
        return k;
    }

    public int grabDecalsFromChunk(int i)
    {
        if(i < 0)
            return 0;
        else
            return GrabDecalsFromChunk(cppObj, i);
    }

    public int applyGrabbedDecalsToChunk(int i)
    {
        if(i < 0)
            return 0;
        else
            return ApplyGrabbedDecalsToChunk(cppObj, i);
    }

    public HierMesh(java.lang.String s)
    {
        super(0);
        chunkMap = null;
        curchunk = 0;
        if(s == null)
            throw new GObjException("Meshname is empty");
        cppObj = Load(s);
        if(cppObj == 0)
        {
            throw new GObjException("HierMesh " + s + " not created");
        } else
        {
            collisionR = CollisionR(cppObj);
            visibilityR = VisibilityR(cppObj);
            com.maddox.il2.engine.Pre.load(s);
            return;
        }
    }

    public HierMesh(com.maddox.il2.engine.HierMesh hiermesh, int i)
    {
        super(0);
        chunkMap = null;
        curchunk = 0;
        if(hiermesh == null)
            throw new GObjException("HierMesh is empty");
        hiermesh.setCurChunk(i);
        cppObj = LoadSubtree(hiermesh.cppObj);
        if(cppObj == 0)
        {
            throw new GObjException("HierMesh (sub) not created");
        } else
        {
            collisionR = CollisionR(cppObj);
            visibilityR = VisibilityR(cppObj);
            return;
        }
    }

    public HierMesh(com.maddox.il2.engine.HierMesh hiermesh, int i, com.maddox.il2.engine.Loc loc)
    {
        super(0);
        chunkMap = null;
        curchunk = 0;
        if(hiermesh == null)
            throw new GObjException("HierMesh is empty");
        hiermesh.setCurChunk(i);
        loc.getMatrix(m4);
        com.maddox.il2.engine.Mesh.tmp[0] = m4.m00;
        com.maddox.il2.engine.Mesh.tmp[4] = m4.m01;
        com.maddox.il2.engine.Mesh.tmp[8] = m4.m02;
        com.maddox.il2.engine.Mesh.tmp[12] = m4.m03;
        com.maddox.il2.engine.Mesh.tmp[1] = m4.m10;
        com.maddox.il2.engine.Mesh.tmp[5] = m4.m11;
        com.maddox.il2.engine.Mesh.tmp[9] = m4.m12;
        com.maddox.il2.engine.Mesh.tmp[13] = m4.m13;
        com.maddox.il2.engine.Mesh.tmp[2] = m4.m20;
        com.maddox.il2.engine.Mesh.tmp[6] = m4.m21;
        com.maddox.il2.engine.Mesh.tmp[10] = m4.m22;
        com.maddox.il2.engine.Mesh.tmp[14] = m4.m23;
        com.maddox.il2.engine.Mesh.tmp[3] = m4.m30;
        com.maddox.il2.engine.Mesh.tmp[7] = m4.m31;
        com.maddox.il2.engine.Mesh.tmp[11] = m4.m32;
        com.maddox.il2.engine.Mesh.tmp[15] = m4.m33;
        cppObj = LoadSubtreeLoc(hiermesh.cppObj, com.maddox.il2.engine.Mesh.tmp);
        if(cppObj == 0)
        {
            throw new GObjException("HierMesh (sub, loc) not created");
        } else
        {
            collisionR = CollisionR(cppObj);
            visibilityR = VisibilityR(cppObj);
            return;
        }
    }

    private native void GetChunkCurVisBoundBox(int i, float af[]);

    private native int Load(java.lang.String s);

    private native int LoadSubtree(int i);

    private native int LoadSubtreeLoc(int i, double ad[]);

    private native int[] HideChunksInSubtrees(int i, java.lang.String s);

    private native int[] GetChunksInSubtrees(int i, java.lang.String s);

    private native int[] GetChunksInSubtreesSpec(int i, java.lang.String s);

    private native void SetPosXYZATK(int i, double d, double d1, double d2, 
            float f, float f1, float f2);

    private native int PreRender(int i);

    private native void Render(int i);

    private native void RenderShadowProjective(int i);

    private native void RenderShadowVolume(int i);

    private native void RenderShadowVolumeHQ(int i);

    private native int DetectCollision(int i, double ad[], int j, double ad1[]);

    private native int DetectCollisionMesh(int i, double ad[], int j, double ad1[]);

    private native float DetectCollisionLine(int i, double ad[], double ad1[], double ad2[]);

    private native int DetectCollisionLineMulti(int i, double ad[], double ad1[], double ad2[]);

    private native float DetectCollisionQuad(int i, double ad[], double ad1[]);

    private native int DetectCollisionQuadMultiH(int i, double ad[], double ad1[]);

    private native int DetectCollisionPoint(int i, double ad[], double ad1[]);

    private native float DetectCollisionPlane(int i, double ad[], double ad1[]);

    private native float VisibilityR(int i);

    private native float CollisionR(int i);

    private native float GetUniformMaxDist(int i);

    private native int Chunks(int i);

    private native void SetCurChunk(int i, int j);

    private native int SetCurChunkByName(int i, java.lang.String s);

    private native int ChunkFind(int i, java.lang.String s);

    private native int ChunkFindCheck(int i, java.lang.String s);

    private native java.lang.String ChunkName(int i);

    private native float GetChunkVisibilityR(int i);

    private native int GetChunkVisibility(int i);

    private native void SetChunkVisibility(int i, int j);

    private native void SetChunkAngles(int i, float af[]);

    private native void SetChunkLocate(int i, float af[], float af1[]);

    private native int Frames(int i);

    private native void SetFrame(int i, int j);

    private native void SetFrame(int i, int j, int k, float f);

    private native void SetFrameFromRange(int i, int j, int k, float f);

    private native int Hooks(int i);

    private native int HookFind(int i, java.lang.String s);

    private native java.lang.String HookName(int i, int j);

    private native void HookMatrix(int i, int j, double ad[]);

    private native void GetChunkLTM(int i, double ad[]);

    private native int ChunkByHookNamed(int i, int j);

    private native int HookFaceCollisFind(int i, double ad[], int ai[], double ad1[]);

    private native int HookFaceFind(int i, double ad[], int ai[], double ad1[]);

    private native int HookChunkFaceFind(int i, double ad[], double ad1[]);

    private native void HookFaceMatrix(int i, int j, int k, double ad[]);

    private native int Materials(int i);

    private native int MaterialFind(int i, java.lang.String s, int j, int k);

    private native int MaterialFindInChunk(int i, java.lang.String s, int j);

    private native java.lang.Object Material(int i, int j);

    private native void MaterialReplace(int i, int j, int k);

    private native void MaterialReplace(int i, int j, java.lang.String s);

    private native void MaterialReplace(int i, java.lang.String s, int j);

    private native void MaterialReplace(int i, java.lang.String s, java.lang.String s1);

    private native float PoseCRC(int i);

    private native int isVisualRayHit(int i, double ad[], double d, double d1, double ad1[]);

    private native int ApplyDecal(int i, int j, float f, float f1, int k, double ad[], float af[], 
            int l, int ai[], int i1);

    private native int GrabDecalsFromChunk(int i, int j);

    private native int ApplyGrabbedDecalsToChunk(int i, int j);

    private native void RenderChunkMirror(int i, double ad[], double ad1[], double ad2[]);

    private static native void RenderShadowPairs(int i, int ai[]);

    private static com.maddox.JGP.Matrix4d m4 = new Matrix4d();
    private static float arrFloat6[] = new float[6];
    private static double arrDouble6[] = new double[6];
    private static double Eul[] = new double[3];
    private static int shadowPairs[] = new int[32];
    private static float _chunkAngles[] = new float[3];
    private com.maddox.util.HashMapExt chunkMap;
    private static com.maddox.il2.engine.Loc _chunkLoc = new Loc();
    private int curchunk;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();

    static 
    {
        com.maddox.il2.engine.GObj.loadNative();
    }

}
