// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookFace.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Hook, HierMesh, ActorMesh, ActorException, 
//            Loc, Orient, Mesh, ActorPos, 
//            Actor

public class HookFace extends com.maddox.il2.engine.Hook
{

    private static float RAD2DEG(float f)
    {
        return f * 57.29578F;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        mesh.hookFaceMatrix(iFace, iChunk, m1);
        loc.getMatrix(m2);
        m2.mul(m1);
        loc1.getMatrix(m1);
        m2.mul(m1);
        m2.getEulers(tmp);
        o.setYPR(com.maddox.il2.engine.HookFace.RAD2DEG((float)tmp[0]), 360F - com.maddox.il2.engine.HookFace.RAD2DEG((float)tmp[1]), 360F - com.maddox.il2.engine.HookFace.RAD2DEG((float)tmp[2]));
        p.set(m2.m03, m2.m13, m2.m23);
        loc1.set(p, o);
    }

    public java.lang.String chunkName()
    {
        if(mesh instanceof com.maddox.il2.engine.HierMesh)
        {
            com.maddox.il2.engine.HierMesh hiermesh = (com.maddox.il2.engine.HierMesh)mesh;
            hiermesh.setCurChunk(iChunk);
            return hiermesh.chunkName();
        } else
        {
            return super.chunkName();
        }
    }

    public int chunkNum()
    {
        return iChunk;
    }

    public void baseChanged(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Mesh mesh1 = ((com.maddox.il2.engine.ActorMesh)actor).mesh();
        if(mesh1 == mesh)
            return;
        if(mesh1 == null)
            throw new ActorException("HookFace not found");
        if(com.maddox.il2.engine.Mesh.isSimilar(mesh1, mesh))
        {
            mesh = mesh1;
        } else
        {
            int j = mesh1.hookFaceFind(mRel, i, m1);
            if(j == -1)
                throw new ActorException("HookFace not found");
            mesh = mesh1;
            iChunk = i[0];
            iFace = j;
            L2F.set(m1);
        }
    }

    public HookFace(com.maddox.il2.engine.ActorMesh actormesh, com.maddox.il2.engine.Loc loc)
    {
        L2F = new Matrix4d();
        mRel = new Matrix4d();
        com.maddox.il2.engine.Loc loc1 = actormesh.pos.getAbs();
        l2.sub(loc, loc1);
        l2.getMatrix(mRel);
        mesh = actormesh.mesh();
        iFace = mesh.hookFaceFind(mRel, i, L2F);
        if(iFace == -1)
        {
            throw new ActorException("HookFace not found");
        } else
        {
            iChunk = i[0];
            return;
        }
    }

    public HookFace(com.maddox.il2.engine.ActorMesh actormesh, com.maddox.il2.engine.Loc loc, boolean flag)
    {
        L2F = new Matrix4d();
        mRel = new Matrix4d();
        com.maddox.il2.engine.Loc loc1 = actormesh.pos.getAbs();
        l2.sub(loc, loc1);
        l2.getMatrix(mRel);
        mesh = actormesh.mesh();
        if(flag)
        {
            iFace = mesh.hookFaceFind(mRel, L2F);
            i[0] = mesh.getCurChunk();
        } else
        {
            iFace = mesh.hookFaceFind(mRel, i, L2F);
        }
        if(iFace == -1)
        {
            throw new ActorException("HookFace not found");
        } else
        {
            iChunk = i[0];
            return;
        }
    }

    private int iFace;
    private int iChunk;
    private com.maddox.il2.engine.Mesh mesh;
    private com.maddox.JGP.Matrix4d L2F;
    private com.maddox.JGP.Matrix4d mRel;
    private static final float PI = 3.141593F;
    private static com.maddox.JGP.Matrix4d m1 = new Matrix4d();
    private static com.maddox.JGP.Matrix4d m2 = new Matrix4d();
    private static com.maddox.il2.engine.Loc l2 = new Loc();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static double tmp[] = new double[3];
    private static int i[] = new int[1];

}
