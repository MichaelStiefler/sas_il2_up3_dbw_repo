// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookNamed.java

package com.maddox.il2.engine;

import com.maddox.JGP.Matrix4d;
import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            Hook, HierMesh, ActorMesh, ActorException, 
//            Orient, Mesh, Loc, Actor

public class HookNamed extends com.maddox.il2.engine.Hook
{

    private static float RAD2DEG(float f)
    {
        return f * 57.29578F;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        mesh.hookMatrix(iHook, m1);
        loc.getMatrix(m2);
        m2.mul(m1);
        loc1.getMatrix(m1);
        m2.mul(m1);
        m2.getEulers(tmp);
        o.setYPR(com.maddox.il2.engine.HookNamed.RAD2DEG((float)tmp[0]), 360F - com.maddox.il2.engine.HookNamed.RAD2DEG((float)tmp[1]), 360F - com.maddox.il2.engine.HookNamed.RAD2DEG((float)tmp[2]));
        p.set(m2.m03, m2.m13, m2.m23);
        loc1.set(p, o);
    }

    public java.lang.String chunkName()
    {
        if(mesh instanceof com.maddox.il2.engine.HierMesh)
        {
            com.maddox.il2.engine.HierMesh hiermesh = (com.maddox.il2.engine.HierMesh)mesh;
            int i = ((com.maddox.il2.engine.HierMesh)mesh).chunkByHookNamed(iHook);
            hiermesh.setCurChunk(i);
            return hiermesh.chunkName();
        } else
        {
            return super.chunkName();
        }
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
            int i = mesh1.hookFind(name);
            if(i == -1)
                throw new ActorException("Hook: " + name + " not found");
            mesh = mesh1;
            iHook = i;
            getChunkNum();
        }
    }

    public java.lang.String name()
    {
        return name;
    }

    public int chunkNum()
    {
        return chunkNum;
    }

    private void getChunkNum()
    {
        if(mesh instanceof com.maddox.il2.engine.HierMesh)
        {
            com.maddox.il2.engine.HierMesh hiermesh = (com.maddox.il2.engine.HierMesh)mesh;
            chunkNum = hiermesh.chunkByHookNamed(iHook);
        } else
        {
            chunkNum = -1;
        }
    }

    public HookNamed(com.maddox.il2.engine.ActorMesh actormesh, java.lang.String s)
    {
        mesh = actormesh.mesh();
        iHook = mesh.hookFind(s);
        if(iHook == -1)
        {
            throw new ActorException("Hook: " + s + " not found");
        } else
        {
            name = s;
            getChunkNum();
            return;
        }
    }

    public HookNamed(com.maddox.il2.engine.Mesh mesh1, java.lang.String s)
    {
        mesh = mesh1;
        iHook = mesh1.hookFind(s);
        if(iHook == -1)
        {
            throw new ActorException("Hook: " + s + " not found");
        } else
        {
            name = s;
            getChunkNum();
            return;
        }
    }

    private int iHook;
    private com.maddox.il2.engine.Mesh mesh;
    private static final float PI = 3.141593F;
    private static com.maddox.JGP.Matrix4d m1 = new Matrix4d();
    private static com.maddox.JGP.Matrix4d m2 = new Matrix4d();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();
    private static double tmp[] = new double[3];
    private java.lang.String name;
    private int chunkNum;

}
