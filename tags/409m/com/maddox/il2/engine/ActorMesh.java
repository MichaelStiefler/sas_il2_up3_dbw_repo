// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorMesh.java

package com.maddox.il2.engine;

import com.maddox.JGP.Vector3f;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, HookNamed, Loc, HookFace, 
//            ActorException, ActorPosStatic, ActorPosMove, ActorMeshDraw, 
//            Mesh, ActorPos, ActorHMesh, HierMesh, 
//            Hook

public abstract class ActorMesh extends com.maddox.il2.engine.Actor
{

    public com.maddox.il2.engine.Mesh mesh()
    {
        return mesh;
    }

    public com.maddox.il2.engine.Hook findHook(java.lang.Object obj)
    {
        if(obj instanceof java.lang.String)
            return new HookNamed(this, (java.lang.String)obj);
        if(obj instanceof com.maddox.il2.engine.Loc)
            return new HookFace(this, (com.maddox.il2.engine.Loc)obj);
        else
            throw new ActorException("Unknown type of hook ident");
    }

    public void getDimensions(com.maddox.JGP.Vector3f vector3f)
    {
        mesh.getDimensions(vector3f);
    }

    public float collisionR()
    {
        return mesh.collisionR();
    }

    public boolean isStaticPos()
    {
        return false;
    }

    public void visibilityAsBase(boolean flag)
    {
        if(((flags & 2) != 0) == flag)
            return;
        super.visibilityAsBase(flag);
        if(pos != null && pos.actor() != this && pos.base() != null)
            if(flag)
                pos.base().pos.addChildren(this);
            else
                pos.base().pos.removeChildren(this);
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        super.destroy();
        if(mesh != null)
        {
            mesh.destroy();
            mesh = null;
        }
    }

    protected ActorMesh()
    {
        mesh = null;
        if(isStaticPos())
            pos = new ActorPosStatic(this);
        else
            pos = new ActorPosMove(this);
        draw = new ActorMeshDraw();
    }

    protected ActorMesh(com.maddox.il2.engine.Loc loc)
    {
        mesh = null;
        if(isStaticPos())
            pos = new ActorPosStatic(this, loc);
        else
            pos = new ActorPosMove(this, loc);
        draw = new ActorMeshDraw();
    }

    protected ActorMesh(com.maddox.il2.engine.ActorPos actorpos)
    {
        mesh = null;
        pos = actorpos;
        draw = new ActorMeshDraw();
    }

    public void setMesh(java.lang.String s)
    {
        boolean flag = mesh() != null && pos != null;
        mesh = new Mesh(s);
        if(flag)
            pos.actorChanged();
    }

    public void setMesh(com.maddox.il2.engine.Mesh mesh1)
    {
        boolean flag = mesh() != null && pos != null;
        mesh = mesh1;
        if(flag)
            pos.actorChanged();
    }

    public ActorMesh(java.lang.String s)
    {
        this();
        try
        {
            mesh = new Mesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorMesh(java.lang.String s, com.maddox.il2.engine.Loc loc)
    {
        this(loc);
        try
        {
            mesh = new Mesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorMesh(java.lang.String s, com.maddox.il2.engine.ActorPos actorpos)
    {
        this(actorpos);
        try
        {
            mesh = new Mesh(s);
        }
        catch(java.lang.RuntimeException runtimeexception)
        {
            super.destroy();
            throw runtimeexception;
        }
    }

    public ActorMesh(com.maddox.il2.engine.ActorHMesh actorhmesh, int i)
    {
        mesh = null;
        actorhmesh.hierMesh().setCurChunk(i);
        actorhmesh.getChunkLocTimeAbs(L1);
        if(isStaticPos())
            pos = new ActorPosStatic(this, L1);
        else
            pos = new ActorPosMove(this, L1);
        mesh = new Mesh(actorhmesh.hierMesh());
        draw = new ActorMeshDraw();
    }

    protected com.maddox.il2.engine.Mesh mesh;
    private static com.maddox.il2.engine.Loc L1 = new Loc();

}
